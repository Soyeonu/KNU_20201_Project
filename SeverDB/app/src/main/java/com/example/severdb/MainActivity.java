package com.example.severdb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button register, login;

    EditText id, pwd;
    CheckBox autologin;

    DbOpenHelper dbOpenHelper;




    //preferenc로 어디서든지 저장된 값 사용할 수 있음!!
    //preference의 내용을 mine이라는 곳에 담아두겠음
    //preference에 저장된 값을 사용하고 싶으면 mine으로 객체 할당해서 사용가능
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //여기서 mine이라는 곳에 preference를 담아두기로 함
        pref = getSharedPreferences("mine",MODE_PRIVATE);
        //preference을 내용을 set, get 하기 위해 필요한 객체
        editor = pref.edit();

        //내부 디비
        dbOpenHelper = new DbOpenHelper(this);
        dbOpenHelper.open();
        dbOpenHelper.create();

        id=findViewById(R.id.editloginid);
        pwd=findViewById(R.id.editloginpw);
        autologin=findViewById(R.id.checklogin);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(onClickListener);
        login.setOnClickListener(onClickListener);

        //preference에 저장된 내용들 중 AutoLogin 부분이 true이면 자동로그인 중임을 의미
        // logout.class 로 전환 됨
        if(pref.getBoolean("AutoLogin",false) == true)
        {
            Intent intent = new Intent(MainActivity.this, logout.class);
            startActivity(intent);
        }

    }

    Button.OnClickListener onClickListener = new Button.OnClickListener(){
        Intent intent;
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.register: //회원가입
                    intent = new Intent(MainActivity.this, LoginRegister.class);
                    startActivity(intent);
                    break;
                case R.id.login: //로그인
                    //입력된 id, pwd를 가져옴
                    String ID = id.getText().toString();
                    String PWD = pwd.getText().toString();
                    //자동 로그인 유무를 판단
                    if(dbOpenHelper.Isthere(ID,PWD)==1) {
                        editor.putString("ID",ID);
                        editor.putString("PWD", PWD);
                        if (autologin.isChecked() == true)
                            // 자동 로그인 선택 후, 로그인 버튼을 누르면 자동로그인 설정됨
                            editor.putBoolean("AutoLogin", true);
                        editor.commit();
                    }


                    //로그인 후, 무조건 로그아웃 화면으로 넘어간다.
                    intent = new Intent(MainActivity.this, logout.class);
                    startActivity(intent);
                    break;
            }
        }
    };

}
