package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import Client_Operation.Connection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mText = findViewById(R.id.signInBtn);
        // 사용자 이름이 있으면 불러와서 세팅
        mText.setText("[ㅇㅇㅇ]로 시작"); // [ㅇㅇㅇ]로 시작

        new Connection().execute("http://ec2-52-78-193-12.ap-northeast-2.compute.amazonaws.com:3000/clientReq", "test", "6");
    }

    public void onClickSign(View v){
        int id = v.getId();
        Intent it;
        // 로그인 혹은 회원 가입 버튼 이벤트
        switch(id){
            case R.id.signInBtn:
                //로그인 화면
                it = new Intent(MainActivity.this, SignIn.class);
                startActivity(it);
                break;
            case R.id.signUpBtn:
                it = new Intent(MainActivity.this, SignUp.class);
                startActivity(it);
        }
    }
}
