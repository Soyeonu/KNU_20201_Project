package com.example.car_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import FB_obj.fb_user;

public class SignUp extends AppCompatActivity {
    private EditText user_email;
    private EditText user_pwd;
    private EditText user_carid;
    private EditText user_name;
    private Button signup_btn;
    FirebaseAuth firebaseAuth;//사용자 인증확인용
    DatabaseReference Fdatabase;//데이터베이스 접근용


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // 회원 가입 창

        user_email = (EditText) findViewById(R.id.id_input);
        user_pwd = (EditText) findViewById(R.id.pwd_input);
        user_carid = (EditText) findViewById(R.id.carid_input);
        user_name = (EditText) findViewById(R.id.name_input);
        signup_btn = (Button) findViewById(R.id.sign_up_btn);
        //나이 폰번 추가 필요

        firebaseAuth = FirebaseAuth.getInstance();
        Fdatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void Create_User_id(View view)
    {
        String name = user_name.getText().toString().trim();
        String email = user_email.getText().toString().trim();
        String pwd = user_pwd.getText().toString().trim();
        String carid = user_carid.getText().toString().trim();


        if(email.equals("") || email == null)
        {
            Toast.makeText(getApplicationContext(), "ID를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pwd.equals("") || pwd == null)
        {
            Toast.makeText(getApplicationContext(), "비밀번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "회원가입 완료! 로그인을 진행해 주세요!",Toast.LENGTH_SHORT).show();
                            //FirebaseUser user = firebaseAuth.getCurrentUser(); 자동로그인
                        }
                        else { //회원가입 조건에 충족하지 않음.

                            Toast.makeText(getApplicationContext(), "회원가입 실패!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        /*
        fb_user New_user = new fb_user(name,null,null);
        Fdatabase.child("user_profile").child("uid").child("phone").setValue("12345");
        Fdatabase.child("user_profile").child("000").child("000").setValue("12345");
        */
        //데이터 입력 테스트

        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        startActivity(intent);
    }
}