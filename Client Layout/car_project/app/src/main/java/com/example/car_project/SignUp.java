package com.example.car_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import Client_Information.Function;
import Client_Information.Permission;
import Client_Information.Profile;
import Client_Information.Registration;
import Client_Information.User;
import FB_obj.fb_user_profile;

public class SignUp extends AppCompatActivity {

    private EditText user_id;
    private EditText user_pwd;// EditText user_pwd;
    private EditText user_email;
    private EditText user_name;
    private EditText user_phone;
    private Button signup_btn;

    private FirebaseAuth firebaseAuth;//사용자 인증확인용
    private DatabaseReference Freference;//데베 접근자
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
        user_email = (EditText) findViewById(R.id.email_input);
        user_pwd = (EditText) findViewById(R.id.pwd_input);
        user_name = (EditText) findViewById(R.id.name_input);
        user_phone = (EditText) findViewById(R.id.phone_input);

        signup_btn = (Button) findViewById(R.id.sign_up_btn);

        //fb_mid = (TextView)findViewById(R.id.invisible_signup_mid);
        //fb_mid_count = (TextView)findViewById(R.id.invisible_signup_midcount);

        firebaseAuth = FirebaseAuth.getInstance();
        Freference = FirebaseDatabase.getInstance().getReference();

     }

    public void Create_User_id(View view)
    {
        String id = user_id.getText().toString().trim();
        String pwd = user_pwd.getText().toString().trim();
        String name = user_name.getText().toString().trim();
        String email = user_email.getText().toString().trim();
        String phone = user_phone.getText().toString().trim();

        String fb_id = id+"@google.com";

        if(id.equals("") || id == null)
        {
            Toast.makeText(getApplicationContext(), "전화번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
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
        if(phone.equals("") || phone == null)
        {
            Toast.makeText(getApplicationContext(), "전화번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }


        Profile profile  = new Profile(name,email,phone);
/*
        Function func1 = new Function("func1","DevNum","Message");
        Function func2 = new Function("func2","DevNum","Message");
        ArrayList<Function> funclist = new ArrayList<Function>();
        funclist.add(func1);
        funclist.add(func2);
        Permission perm = new Permission("uid",funclist);

        ArrayList<Registration> reg = new ArrayList<Registration>();
        Registration data1 = new Registration("mid1","2020/09/05",false,perm,true,true);
        Registration data2 = new Registration("mid1","2020/12/12",false,perm,true,true);
        reg.add(data1);
        reg.add(data2);
*/
        User newUser = new User(id,pwd,profile);

        if (newUser.update()) {
            firebaseAuth.createUserWithEmailAndPassword(fb_id, pwd)
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
            Toast.makeText(getApplicationContext(), "DB 갱신 오류", Toast.LENGTH_SHORT).show();
        }

    }


/*
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
    */

}