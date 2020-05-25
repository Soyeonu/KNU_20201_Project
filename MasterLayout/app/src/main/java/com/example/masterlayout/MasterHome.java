package com.example.masterlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import FB_obj.fb_master_info;

public class MasterHome extends AppCompatActivity implements RelativeLayout.OnClickListener {
    TextView timeView;
    TextView dateView;

    ///////////////////
    private String mid;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference Fdatabase;
    private fb_master_info info;
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
        musicLayout.setOnClickListener(this);
        RelativeLayout videoLayout = findViewById(R.id.videoLayout);
        videoLayout.setOnClickListener(this);
        RelativeLayout airconLayout = findViewById(R.id.airconLayout);
        airconLayout.setOnClickListener(this);

        ImageButton preferences = findViewById(R.id.preferenceBtn);
        preferences.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent change
            }
        });

        ///////////////////////////////
        firebaseAuth = FirebaseAuth.getInstance();
        Fdatabase = FirebaseDatabase.getInstance().getReference();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        FirebaseUser fbuser = firebaseAuth.getCurrentUser();
        Toast.makeText(getApplicationContext(),"반갑습니다!", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        mid = intent.getExtras().getString("mid");

        info = new fb_master_info(mid, (float) 25.5,0,0,null,1);
        info.update();
        //fb_message_listener();        //사용자로부터 메시지 수신 리스너
        ////////////////////////////
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
        info = new fb_master_info(mid, (float) 25.5,0,0,null,0); //로갓 상태로 만들기
        info.update();
    }
}
