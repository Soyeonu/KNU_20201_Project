package com.example.masterlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import MasterInfo.Masterinfo;

public class MainActivity extends AppCompatActivity {

    TextView timeView;
    TextView dateView;

    ///////////////////
    private String mid = "master001";
    private Masterinfo info;
    //////////////////
    private float airtemp;
    private int seatbelt;
    private int airpower;
    private String medialink;
    //////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_home);

        timeView = findViewById(R.id.timeText);
        dateView = findViewById(R.id.dateText);

        UpdateTimeMethod();

        RelativeLayout musicLayout = findViewById(R.id.musicLayout);
        musicLayout.setOnClickListener((View.OnClickListener) this);
        RelativeLayout videoLayout = findViewById(R.id.videoLayout);
        videoLayout.setOnClickListener((View.OnClickListener) this);
        RelativeLayout airconLayout = findViewById(R.id.airconLayout);
        airconLayout.setOnClickListener((View.OnClickListener) this);

        ImageButton preferences = findViewById(R.id.preferenceBtn);
        preferences.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent change
            }
        });

        fb_msg_listener();        //사용자로부터 메시지 수신 리스너
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


    public void onClick(View v) {
        Intent it;
        switch(v.getId()){
            case R.id.musicLayout:
                System.out.println("music");
                it = new Intent(this, MusicActivity.class);
                startActivity(it);
                break;
            case R.id.videoLayout:
                System.out.println("video");
                it = new Intent(this, VideoActivity.class);
                startActivity(it);
                break;
            case R.id.airconLayout:
                System.out.println("aircon");
                it = new Intent(this, AirconActivity.class);
                startActivity(it);
                break;
            case R.id.funcLayout:
                System.out.println("func1");
                break;
            case R.id.func2Layout:
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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Message").child(mid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                {
                    System.out.println("Listener Triggered");
                    System.out.println(childDataSnapshot.toString());
                    String msg = (String) childDataSnapshot.child("Msg").getValue();
                    String val_str = (String) childDataSnapshot.child("Value").getValue();

                    switch(msg){
                        //각 메시지별로 적절한 센서 구동시키는 메소드 삽입
                        case "TEMP_CHK":
                            //현재 에어컨 온도를 확인해 서버로 return하는 메소드
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Master").child("TEMP");
                            System.out.println(msg + " is accepted.");
                            //아두이노 모듈에서 값을 받아와 아래 setValue에 파라메터로 삽입 (테스트값 25)
                            ref.setValue(25);
                            //Master-TEMP에 값을 쓰는 순간 Wait하고 있던 서버에서 이를 감지해 Client에게 해당 값을 돌려줄 것임
                            break;
                        case "TEMP_DST":
                            //에어컨 온도 조정하는 기능을 담당하는 메소드
                            //아두이노 모듈에 Msg와 Val을 전달해 모듈 내부 값 조정
                            //(해당 메소드)
                            break;
                        //기타 등등 아래에 case문으로 추가하면 됨
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
