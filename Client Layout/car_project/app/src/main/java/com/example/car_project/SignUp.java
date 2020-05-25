package com.example.car_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import FB_obj.fb_user_permission;
import FB_obj.fb_user_profile;

public class SignUp extends AppCompatActivity {

    private EditText user_id;
    private EditText user_mail;//ID부

    private EditText user_pwd;
    private EditText user_carid;
    private EditText user_name;
    private EditText user_phone;
    private Button signup_btn;

    private FirebaseAuth firebaseAuth;//사용자 인증확인용
    private DatabaseReference Fdatabase;//데베 접근자
    private static final String TAG = "SignUp";


    private TextView fb_mid;
    private TextView fb_mid_count;

    private ArrayList<String> midarry = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // 회원 가입 창

        user_id = (EditText) findViewById(R.id.id_input);
        user_mail = (EditText) findViewById(R.id.mail_input);
        user_pwd = (EditText) findViewById(R.id.pwd_input);
        user_carid = (EditText) findViewById(R.id.carid_input);
        user_name = (EditText) findViewById(R.id.name_input);
        user_phone = (EditText) findViewById(R.id.phone_input);
        signup_btn = (Button) findViewById(R.id.sign_up_btn);

        fb_mid = (TextView)findViewById(R.id.invisible_signup_mid);
        fb_mid_count = (TextView)findViewById(R.id.invisible_signup_midcount);

        firebaseAuth = FirebaseAuth.getInstance();
        Fdatabase = FirebaseDatabase.getInstance().getReference();
        fb_registration_listener();
    }

    public void Create_User_id(View view)
    {
        String name = user_name.getText().toString().trim();
        String id = user_id.getText().toString().trim();
        String mail = user_mail.getText().toString().trim();
        String pwd = user_pwd.getText().toString().trim();
        String phone = user_phone.getText().toString().trim();
        String carid = user_carid.getText().toString().trim();
        String email = id+"@"+mail;

        if(email.equals("") || email == null)
        {
            Toast.makeText(getApplicationContext(), "email을 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pwd.equals("") || pwd == null)
        {
            Toast.makeText(getApplicationContext(), "비밀번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(name.equals("") || name == null)
        {
            Toast.makeText(getApplicationContext(), "이름을 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(phone.equals("") || name == null)
        {
            Toast.makeText(getApplicationContext(), "전화번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean register = Register_on_Fb(id, mail,name,phone,carid); //carid가 이미 등록된것인가?

        if (register == true) {
            firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "회원가입 완료! 로그인을 진행해 주세요!", Toast.LENGTH_SHORT).show();
                                //FirebaseUser user = firebaseAuth.getCurrentUser(); 자동로그인
                                finish();
                                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);
                            } else { //회원가입 조건에 충족하지 않음.

                                Toast.makeText(getApplicationContext(), "회원가입 실패!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "이미 주인이 존재하는 차량입니다.", Toast.LENGTH_SHORT).show();
        }

    }

    //user_data에 추가
    public boolean Register_on_Fb(String ID,String Mail, String Name, String Phone, String Carid)
    {
        String Email = ID+"@"+Mail;
        if (Carid.equals("") || Carid == null)          //carid 미입력
        {
            fb_user_profile profile = new fb_user_profile(Email,Name,Phone,Carid);
            Fdatabase.child("user_data").child(ID).setValue(profile);
        }
        else                //리스너로부터 carid가 이미 owner가 있는가를 검사
        {
            fb_user_permission perm = new fb_user_permission("1","1","1","1",Carid,Email,"owner","2025/01/01");
            fb_user_profile profile = new fb_user_profile(Email,Name,Phone,Carid);

            Log.v(TAG, "method :"+midarry); //displays the key for the node

            if (midarry.size() != 0)
            {
                for(String i : midarry)
                {
                    Log.v(TAG + "i :", (String) i);
                    if(i.equals(Carid)) //이미 존재하는 carid
                    {
                        return false;
                    }
                }
            }

            Fdatabase.child("user_data").child(ID).setValue(profile);
            Fdatabase.child("user_permission").push().setValue(perm);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_data");
            return true;
        }
        return true;
    }

    //carid 검사를 위한 리스너
    public void fb_registration_listener()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_data");
        Query query = ref.orderByChild("mid");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                {
                    //Toast.makeText(getApplicationContext(), (String)childDataSnapshot.child("phone").getValue(), Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "in listener" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v(TAG, "in listener" + childDataSnapshot.child("email").getValue());   //gives the value for given keyname
                    if (childDataSnapshot != null)
                    {
                        String Input = (String) childDataSnapshot.child("mid").getValue();
                        Create_midarr(Input,i);
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public void Create_midarr(String Input, int Count) {
        fb_mid.setText(Input);
        String temp = (String)Integer.toString(Count);
        fb_mid_count.setText(temp);

        int cnt = Integer.parseInt(fb_mid_count.getText().toString());
        String input = fb_mid.getText().toString();

        midarry.add(cnt,input);
    }
}