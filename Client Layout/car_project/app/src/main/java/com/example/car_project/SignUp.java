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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Client_Information.Function;
import Client_Information.Permission;
import Client_Information.Profile;
import Client_Information.Registration;
import Client_Information.User;

public class SignUp extends AppCompatActivity {

    private EditText user_id;
    private EditText user_pwd;// EditText user_pwd;
    private EditText user_email;
    private EditText user_name;
    private EditText user_phone;
    private EditText master_id;
    private Button signup_btn;

    private FirebaseAuth firebaseAuth;//사용자 인증확인용
    private DatabaseReference Freference;//데베 접근자
    private static final String TAG = "SignUp";

    private HashMap<String,Object> map;

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
        master_id = (EditText)findViewById(R.id.master_input);

        signup_btn = (Button) findViewById(R.id.sign_up_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        Freference = FirebaseDatabase.getInstance().getReference();
        map = new HashMap<String, Object>();
        Alluser_listener();
     }

    public void Create_User_id(View view) {
        String id = user_id.getText().toString().trim();
        String pwd = user_pwd.getText().toString().trim();
        String name = user_name.getText().toString().trim();
        String email = user_email.getText().toString().trim();
        String phone = user_phone.getText().toString().trim();
        String masterid = master_id.getText().toString().trim();
        ArrayList<Registration> reglist = new ArrayList<Registration>();

        String fb_id = id + "@google.com";

        if (id.equals("") || id == null) {
            Toast.makeText(getApplicationContext(), "전화번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.equals("") || email == null) {
            Toast.makeText(getApplicationContext(), "email을 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.equals("") || pwd == null) {
            Toast.makeText(getApplicationContext(), "비밀번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.equals("") || name == null) {
            Toast.makeText(getApplicationContext(), "이름을 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.equals("") || phone == null) {
            Toast.makeText(getApplicationContext(), "전화번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (masterid != null) {
            for (Map.Entry<String, Object> data : map.entrySet()) { // 모든 유저에 대해 reglist에서 masterid가 입력한 masterid와 같고, primary, 주인이 존재하는지를 본다. 없다면 새로운 주인을 입력한 해당 id에 부여한다.
                System.out.println(String.format("키 : %s, 값 : %s", data.getKey(), data.getValue()));
                User olduser = (User) data.getValue();
                ArrayList<Registration> list = olduser.gain_list();
                int list_count = list.size();
                if (list_count != 0) {
                    for (int index = 0; index < list_count; index++) {
                        System.out.println(list.get(index));
                        Registration reg = list.get(index);
                        String mid = reg.gain_masterid();
                        Boolean primary = reg.gain_primary();

                        if (masterid.equals(mid) && primary == true) {
                            Toast.makeText(getApplicationContext(), mid + "의 주인은 " + olduser.gain_userid() + "입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }
            //검사가 끝났다면 주인으로 추가함
            HashMap<String,Boolean> perm = new HashMap<String,Boolean>();
            perm.put("air_temp",true);
            perm.put("air_power",true);
            perm.put("seatbelt",true);
            perm.put("play_media",true);
            Registration regdata = new Registration(masterid,"2020/09/05",false,perm,true,false);
            reglist.add(regdata);
        }
        /*
        Function func1 = new Function("func1","DevNum","Message");
        Function func2 = new Function("func2","DevNum","Message");
        ArrayList<Function> funclist = new ArrayList<Function>();
        funclist.add(func1);
        funclist.add(func2);
        Permission perm = new Permission(id,funclist);

        ArrayList<Registration> reg = new ArrayList<Registration>();
        Registration data1 = new Registration("mid1","2020/09/05",false,perm,true,true);
        Registration data2 = new Registration("mid1","2020/12/12",false,perm,true,true);
        reg.add(data1);
        reg.add(data2);
 */
            Profile profile = new Profile(name, phone, email);
            User newUser = new User(id, pwd, profile,reglist);

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
            } else {
                Toast.makeText(getApplicationContext(), "DB 갱신 오류", Toast.LENGTH_SHORT).show();
            }
    }

    public void Alluser_listener() {                        //user_data 하위 모든 유저 객체를 참조하여 Hashmap에 저장한다.
        Freference = Freference.child("user_data");
        Freference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(), (String)childDataSnapshot.child("phone").getValue(), Toast.LENGTH_SHORT).show();
                //Log.v(TAG, "in listener" + childDataSnapshot.getKey()); //displays the key for the node
                //Log.v(TAG, "in listener" + childDataSnapshot.child("email").getValue());   //gives the value for given keyname
                if (dataSnapshot != null)
                {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        User user = data.getValue(User.class);
                        map.put(user.gain_userid(),user);
                    }
                    Log.v(TAG, "in listener + map" + map);
                }
                /*
                else
                {
                    Freference.removeEventListener(this);
                }
                 */
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}