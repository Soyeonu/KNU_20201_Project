package com.example.masterlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import MasterInfo.Masterinfo;

public class MainActivity extends AppCompatActivity implements RelativeLayout.OnClickListener{

    public Socket mSocket;
    public String mServerIP;
    public String mServerPort;
    public PrintWriter mOut;
    public BufferedReader mIn;
    public Thread mRecieverThread = null;
    public boolean isConnected = false;

    TextView timeView;
    TextView dateView;

    ///////////////////
    private String mid = "master1";
    private Masterinfo info;
    //////////////////

    ////////////////// 아두이노
    RelativeLayout musicLayout;
    RelativeLayout videoLayout;
    RelativeLayout airconLayout;
    RelativeLayout seatLayout;
    String recvMessage;

    ////////////////// 테스트용
    EditText editText;
    TextView seatText;
    TextView tempText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeView = findViewById(R.id.timeText);
        dateView = findViewById(R.id.dateText);

        UpdateTimeMethod();

        musicLayout= findViewById(R.id.musicLayout);
        musicLayout.setOnClickListener(this);
        videoLayout = findViewById(R.id.videoLayout);
        videoLayout.setOnClickListener(this);
        airconLayout = findViewById(R.id.airconLayout);
        airconLayout.setOnClickListener(this);
        seatLayout = findViewById(R.id.seatLayout);
        seatLayout.setOnClickListener(this);

        editText = findViewById(R.id.edit);
        seatText = findViewById(R.id.seatText);
        tempText = findViewById(R.id.tempText);
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);

        /*ImageButton preferences = findViewById(R.id.preferenceBtn);
        preferences.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent change
            }
        });*/

        //fb_msg_listener();        //사용자로부터 메시지 수신 리스너
        new Thread(new ConnectThread("192.168.4.1", 80)).start();
    }

    public void UpdateTimeMethod(){
        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                Date date = new Date();
                // 시간 구하기
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.KOREA);
                timeView.setText(timeFormat.format(date));

                // 날짜 요일 구하기
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 EE요일", Locale.KOREA);
                dateView.setText(dateFormat.format(date));

            }
        };
        Runnable task = new Runnable(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){}
                    handler.sendEmptyMessage(1);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.musicLayout:
                break;
            case R.id.videoLayout:
                break;
            case R.id.airconLayout:
                break;
            case R.id.btn:
                String message = editText.getText().toString();
                Log.d("MSG",message);
                if(message.length() > 0)
                {
                    if(!isConnected){
                        showErrorDialog("서버접속바람");
                    }
                    else
                    {
                        new Thread(new SenderThread(message)).start();
                        editText.setText(" ");
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        info = new Masterinfo(mid, (float) -999,-1,-1,null,0); //로갓 상태로 만들기
        info.update();
    }

    public void fb_msg_listener(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Message").child(mid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Listener Triggered");
                System.out.println(dataSnapshot.toString());
                //msg가 아두이노로 보내는 메세지
                String msg = dataSnapshot.child("Msg").getValue().toString();
                String val_str = dataSnapshot.child("Value").toString();

                switch(msg) {
                    //각 메시지별로 적절한 센서 구동시키는 메소드 삽입
                    case "TEMP_CHK":
                        //현재 에어컨 온도를 확인해 서버로 return하는 메소드
                        DatabaseReference tempref = FirebaseDatabase.getInstance().getReference().child("Master").child("TEMP");System.out.println(msg + " is accepted.");
                        //아두이노 모듈에서 값을 받아와 아래 setValue에 파라메터로 삽입 (테스트값 25)
                        tempref.setValue(25);
                        //Master-TEMP에 값을 쓰는 순간 Wait하고 있던 서버에서 이를 감지해 Client에게 해당 값을 돌려줄 것임

                        //여기서부터 메세지 보내기
                        if(msg.length() > 0)
                        {
                            if(!isConnected){
                                showErrorDialog("서버접속바람");
                            }
                            else
                            {
                                new Thread(new SenderThread(msg)).start();
                                editText.setText(" ");
                            }
                        }
                        break;
                    case "TEMP_DST":
                        // TEMP_DST/value 형식으로 보내야함
                        if(msg.length() > 0)
                        {
                            if(!isConnected){
                                showErrorDialog("서버접속바람");
                            }
                            else
                            {
                                new Thread(new SenderThread(msg)).start();
                                editText.setText(" ");
                            }
                        }
                        break;
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private class ConnectThread implements Runnable{
        private String serverIP;
        private int serverPort;

        ConnectThread(String ip, int port)
        {
            this.serverIP = ip;
            this.serverPort = port;
        }

        @Override
        public void run() {
            try{
                mSocket = new Socket(serverIP, serverPort);
                mServerIP = mSocket.getRemoteSocketAddress().toString();
                Log.d("connect","connect");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(mSocket!=null)
            {
                try {
                    mOut = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(mSocket.getOutputStream(),"UTF-8")),true);
                    mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    isConnected = true;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(isConnected)
                        new Thread(new ReceiverThread()).start();
                }
            });
        }
    }

    private class SenderThread implements Runnable{
        private String msg;

        SenderThread(String msg)
        {
            this.msg = msg;
        }
        @Override
        public void run() {
            mOut.println(this.msg);
            mOut.flush();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }

    private class ReceiverThread implements Runnable{
        @Override
        public void run() {
            try{
                while(isConnected){
                    if(mIn == null) {
                        Log.d("RecvTest","Break");
                    }
                    recvMessage = mIn.readLine();
                    Log.d("Recv",recvMessage);
                    if(recvMessage != null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //여기서 클라이언트한테 값 전달
                                //지금은 마스터로 보내놨음
                                //원한다면 마스터에서 줍줍 가능~
                                if(recvMessage.equals("ON\n"))
                                {
                                    tempText.setText(recvMessage);
                                    airconLayout.setBackgroundColor(Color.BLUE);//근데 이거 색 왜 안 바뀜?ㅡㅡ
                                }
                                else if(recvMessage.equals("OFF\n"))
                                {
                                    tempText.setText(recvMessage);
                                    airconLayout.setBackgroundColor(Color.RED);//근데 이거 색 왜 안 바뀜?ㅡㅡ
                                }
                                else
                                {
                                    tempText.setText(recvMessage);
                                }
                            }
                        });
                    }

                }

                if(mOut!=null)
                {
                    mOut.flush();;
                    mOut.close();
                }

                if(mSocket!=null)
                {
                    try {
                        mSocket.close();
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showErrorDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }
}
