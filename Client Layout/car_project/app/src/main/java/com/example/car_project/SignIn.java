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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Client_Information.*;

public class SignIn extends AppCompatActivity {

    private EditText eTextId;
    private EditText eTextPw;
    private CheckBox autoLogin;
    FirebaseAuth firebaseAuth;
    private DatabaseReference Freference;
    private static final String TAG = "SignIn";

    String test;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        Freference = FirebaseDatabase.getInstance().getReference();

        user = new User();

        test_listener();
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
        String id_input=null;
        String pwd_input=null;
        final String TAG = "SignIn";


        switch(id){
            case R.id.loginBtn:
                eTextId = findViewById(R.id.id);
                eTextPw = findViewById(R.id.pw);
                id_input = eTextId.getText().toString().trim();
                pwd_input = eTextPw.getText().toString().trim();
                if(id_input.equals("") || id_input == null)
                {
                    Toast.makeText(getApplicationContext(), "ID를 채워주세요!", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(pwd_input.equals("") || pwd_input == null)
                {
                    Toast.makeText(getApplicationContext(), "비밀번호를 채워주세요!", Toast.LENGTH_SHORT).show();
                    break;
                }

                System.out.println("id: = " + eTextId.getText().toString() + " pw: = " + eTextPw.getText().toString());
                //eTextId와 eTextPw에 id와 pw
                /*
                autoLogin = findViewById(R.id.autoCheck);
                if(autoLogin.isChecked()){
                    //나중에 자동 로그인 처리
                    System.out.println("Auto Checked");
                }
                 */
                final String finalId_input = id_input;
                firebaseAuth.signInWithEmailAndPassword(id_input+"@google.com", pwd_input)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){                // 로그인 성공 하면 차 기능으로 넘어가기.
                                    Toast.makeText(getApplicationContext(), "인증 성공!", Toast.LENGTH_SHORT).show();
                                    //FirebaseUser user = firebaseAuth.getCurrentUser();

                                    Log.d(TAG, eTextId.getText().toString());
                                    Intent intent = new Intent(getApplicationContext(), mid_select.class);
                                    intent.putExtra("uid", finalId_input);
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
        Log.v(TAG, "in button + " + user.get_profile().get_username());
    }

    public void test_listener() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_data").child("test");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Toast.makeText(getApplicationContext(), (String)childDataSnapshot.child("phone").getValue(), Toast.LENGTH_SHORT).show();
                    //Log.v(TAG, "in listener" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v(TAG, "in listener" + childDataSnapshot.child("email").getValue());   //gives the value for given keyname
                    if (dataSnapshot != null)
                    {
                        Log.v(TAG, "in listener + " + dataSnapshot.getKey()); //displays the key for the node
                        User userinfo = dataSnapshot.getValue(User.class);
                        test = userinfo.get_userid();
                        user = userinfo;
                        Log.v(TAG," + " + userinfo );
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
