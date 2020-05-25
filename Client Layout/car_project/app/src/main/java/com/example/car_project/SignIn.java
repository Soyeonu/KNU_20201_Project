package com.example.car_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Client_Information.*;

public class SignIn extends AppCompatActivity {

    private EditText eTextId;
    private EditText eTextPw;
    private CheckBox autoLogin;
    FirebaseAuth firebaseAuth;
    private DatabaseReference Fdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        Fdatabase = FirebaseDatabase.getInstance().getReference();
    }
/*
    public void onStart(){
        super.onStart();
        //유저가 이미 접속중인지 확인(추가?)
        FirebaseUser current_user = firebaseAuth.getCurrentUser();
        if (current_user == null) {
            // User is signed in.
            String email = current_user.getEmail();
            Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
            //firebaseAuth.signOut();
        } else {
            // No user is signed in.
            Toast.makeText(getApplicationContext(), "not any one", Toast.LENGTH_SHORT).show();
        }

    }*/

    public void onClickLogin(View v){
        int id = v.getId();
        //Intent it;
        String email=null;
        String pwd=null;
        final String TAG = "SignIn";

        switch(id){
            case R.id.loginBtn:
                eTextId = findViewById(R.id.id);
                eTextPw = findViewById(R.id.pw);
                email = eTextId.getText().toString().trim();
                pwd = eTextPw.getText().toString().trim();
                if(email.equals("") || email == null)
                {
                    Toast.makeText(getApplicationContext(), "Email을 채워주세요!", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(pwd.equals("") || pwd == null)
                {
                    Toast.makeText(getApplicationContext(), "비밀번호를 채워주세요!", Toast.LENGTH_SHORT).show();
                    break;
                }
                pwd = eTextPw.getText().toString().trim();
                System.out.println("id: = " + eTextId.getText().toString() + " pw: = " + eTextPw.getText().toString());
                //eTextId와 eTextPw에 id와 pw
                /*
                autoLogin = findViewById(R.id.autoCheck);
                if(autoLogin.isChecked()){
                    //나중에 자동 로그인 처리
                    System.out.println("Auto Checked");
                }
                 */
                firebaseAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){                // 로그인 성공 하면 차 기능으로 넘어가기.
                                    Toast.makeText(getApplicationContext(), "인증 성공!", Toast.LENGTH_SHORT).show();
                                    //FirebaseUser user = firebaseAuth.getCurrentUser();

                                    Log.d(TAG, eTextId.getText().toString());
                                    Intent intent = new Intent(getApplicationContext(), mid_select.class);
                                    finish();
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "인증 실패!", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, eTextId.getText().toString());
                                }
                            }
                        });
                break;
        }
    }



}
