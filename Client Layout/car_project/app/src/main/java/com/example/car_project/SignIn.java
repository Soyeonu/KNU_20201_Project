package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SignIn extends AppCompatActivity {
    private EditText eTextId;
    private EditText eTextPw;
    private CheckBox autoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
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
                autoLogin = findViewById(R.id.autoCheck);
                if(autoLogin.isChecked()){
                    //나중에 자동 로그인 처리
                    System.out.println("Auto Checked");
                }


                // 로그인 성공 하면 차 기능으로 넘어가기
                it = new Intent(this, car_func.class);
                startActivity(it);
                break;
        }
    }
}
