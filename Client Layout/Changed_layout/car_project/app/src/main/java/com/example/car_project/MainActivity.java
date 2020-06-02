package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import Client_Information.Profile;
import Client_Information.User;

public class MainActivity extends AppCompatActivity {
    Button login;
    private EditText eTextId;
    private EditText eTextPw;
    private CheckBox autoLogin;

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

        eTextId=findViewById(R.id.id);
        eTextPw=findViewById(R.id.pw);
        autoLogin=findViewById(R.id.autoCheck);
        login = findViewById(R.id.loginBtn);

        //preference에 저장된 내용들 중 AutoLogin 부분이 true이면 자동로그인 중임을 의미
        // logout.class 로 전환 됨
//        if(pref.getBoolean("AutoLogin",false) == true)
//        {
//            Intent intent = new Intent(this, car_func.class);
//            startActivity(intent);
//        }

    }

    public void onClickLogin(View v){
        int id = v.getId();
        Intent it;
        switch(id){
            case R.id.loginBtn:
                eTextId = findViewById(R.id.id);
                eTextPw = findViewById(R.id.pw);
                System.out.println("id: = " + eTextId.getText().toString() + " pw: = " + eTextPw.getText().toString());
                //eTextId와 eTextPw에 id와 pw가 들어감
//                autoLogin = findViewById(R.id.autoCheck);
//                if(autoLogin.isChecked())
//                    //나중에 자동 로그인 처리
//                    editor.putBoolean("AutoLogin", true);
//                editor.commit();

                // 로그인 성공 하면 차 선택창으로 넘어가기
                it = new Intent(this, CarSelect.class);
                startActivity(it);
                break;

            case R.id.signupBtn:
                it = new Intent(MainActivity.this, SignUp.class);
                startActivity(it);
        }
    }
}

