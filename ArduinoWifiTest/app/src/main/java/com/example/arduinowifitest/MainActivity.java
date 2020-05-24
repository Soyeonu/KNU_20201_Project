package com.example.arduinowifitest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    public Socket mSocket;
    public String mServerIP;
    public String mServerPort;
    public PrintWriter mOut;
    public BufferedReader mIn;
    public Thread mRecieverThread = null;
    public boolean isConnected = false;

    TextView sendtext;
    TextView receivetext;
    EditText editText;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendtext = findViewById(R.id.texts);
        receivetext = findViewById(R.id.textr);
        editText = findViewById(R.id.edit);
        send = findViewById(R.id.btn);
        send.setOnClickListener(btnlistener);

        //ESP8266세팅할 때 했던 ip주소와 port번호 입력해야함
        new Thread(new ConnectThread("192.168.4.1", 80)).start();
    }

    Button.OnClickListener btnlistener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn:
                    String message = editText.getText().toString();
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
    };
    private class ConnectThread implements Runnable{
        private String serverIP;
        private int serverPort;

        ConnectThread(String ip, int port)
        {
            this.serverIP = ip;
            this.serverPort = port;
            Log.d("Connect", ip +" --- " + port);
        }

        @Override
        public void run() {
            try{
                mSocket = new Socket(serverIP, serverPort);
                if(mSocket==null) Log.d("Socket", "Not make");
                mServerIP = mSocket.getRemoteSocketAddress().toString();
                Log.d("****", mServerIP);
            } catch (UnknownHostException e) {
                Log.d("ConnectError", "ConnectError");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("ConnectError", "ConnectError");
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
                    sendtext.setText(msg);
                }
            });
        }
    }

    private class ReceiverThread implements Runnable{
        @Override
        public void run() {
            try{
                while(isConnected){

                    System.out.println("======================================");
                    if(mIn == null) {
                        Log.d("RecvTest","Break");
                        //break;
                    }
                    final String recvMessage = mIn.readLine();
                    Log.d("Recv",recvMessage);
                    if(recvMessage != null)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                receivetext.setText(recvMessage);
                            }
                        });
                    }
                    else
                        Log.d("mInError", "String is no");
                }

                if(!isConnected) Log.d("recv","recv is not connected");

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
