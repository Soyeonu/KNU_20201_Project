package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import Client_Information.Profile;
import Client_Information.User;

public class MainActivity extends AppCompatActivity {
    Button login;
    private EditText eTextId;
    private EditText eTextPw;
    private CheckBox autoLogin;
    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTextId=findViewById(R.id.id);
        eTextPw=findViewById(R.id.pw);
        autoLogin=findViewById(R.id.autoCheck);
        login = findViewById(R.id.loginBtn);


    }

    public void onClickLogin(View v){
        int id = v.getId();
        Intent it;
        switch(id){
            case R.id.loginBtn:
                eTextId = findViewById(R.id.id);
                eTextPw = findViewById(R.id.pw);
                System.out.println("id: = " + eTextId.getText().toString() + " pw: = " + eTextPw.getText().toString());


                // 로그인 성공 하면 차 선택창으로 넘어가기
                it = new Intent(this, CarSelect.class);
                startActivity(it);
                break;

            case R.id.signupBtn:
                it = new Intent(MainActivity.this, SignUp.class);
                startActivity(it);
        }
    }

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼 기능
      //  super.onBackPressed();

        if(System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\'버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        // 2초 이내로 다시 뒤로가기 버튼 누르면 toast 취소
        // 표시된 toast 취소
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
            toast.cancel();
        }
    }
}

