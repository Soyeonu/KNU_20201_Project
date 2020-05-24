package com.example.car_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import FB_obj.fb_user_profile;

public class car_func extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navView;

    private FragmentManager fm;
    private FragmentTransaction ft;
    car_func_home carfr;
    car_func_setting setfr;
    car_func_user userfr;
    car_func_mycar mycarfr;
    private static final String TAG = "car_func";

    /////////////////
    FirebaseAuth firebaseAuth;//사용자 인증확인용
    DatabaseReference Fdatabase;//데이터베이스 접근용
    String username;
    String useremail;
    //////////////////
    TextView header_name;
    TextView header_email;
    TextView header_masterid;
    //////////////////fb 리스너 비동기 정보를 가져오기 위한 투명 텍스트뷰
    TextView fb_username;
    TextView fb_useremail;
    TextView fb_userphone;
    TextView fb_usermasterid;
    //////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_func);

        car_func_home frHome = new car_func_home();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentlayout, frHome).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 내비게이션 뷰
        drawerLayout = findViewById(R.id.drawer);
        navView = findViewById(R.id.navigationView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);

        navView.setBackgroundColor(Color.rgb(242, 242, 242));
        navView.setItemIconTintList(null);
        fm = getSupportFragmentManager();

        firebaseAuth = FirebaseAuth.getInstance();
        Fdatabase = FirebaseDatabase.getInstance().getReference();
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignIn.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser fbuser = firebaseAuth.getCurrentUser();
        Toast.makeText(getApplicationContext(),"반갑습니다.\n"+ fbuser.getEmail()+"으로 로그인 하였습니다.", Toast.LENGTH_SHORT).show();


        View header = navView.getHeaderView(0);
        header_name = (TextView)header.findViewById(R.id.user_name);
        header_email = (TextView)header.findViewById(R.id.user_email);
        header_masterid = (TextView)header.findViewById(R.id.user_masterid);

        fb_username = (TextView)header.findViewById(R.id.invisible_name);
        fb_useremail = (TextView)header.findViewById(R.id.invisible_email);
        fb_userphone = (TextView)header.findViewById(R.id.invisible_phone);
        fb_usermasterid = (TextView)header.findViewById(R.id.invisible_masterid);

        fb_userdata_listener();

        //Log.v(TAG, "oncreate header name : "+ header_name.toString());
    }

    public void fb_userdata_listener() {
        useremail = firebaseAuth.getCurrentUser().getEmail();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_data");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    //Toast.makeText(getApplicationContext(), (String)childDataSnapshot.child("phone").getValue(), Toast.LENGTH_SHORT).show();
                    //Log.v(TAG, "in listener" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v(TAG, "in listener" + childDataSnapshot.child("email").getValue());   //gives the value for given keyname
                    if (childDataSnapshot != null)
                    {
                        if(useremail.equals(childDataSnapshot.child("email").getValue()))
                        {
                            HashMap<String,Object> InputMap = new HashMap<>();
                            String email = (String) childDataSnapshot.child("email").getValue();
                            String name = (String) childDataSnapshot.child("name").getValue();
                            String phone = (String) childDataSnapshot.child("phone").getValue();
                            String masterid= (String) childDataSnapshot.child("mid").getValue();//이 경우 intent로 차량의 mid를 받았다고 가정해야 함.
                            fb_user_profile profile = new fb_user_profile(email,name,phone,masterid);

                            InputMap = (HashMap<String, Object>) profile.toMap();
                            set_invisible_TextViews(InputMap);
                            set_TextViews(InputMap);

                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void set_invisible_TextViews(HashMap<String,Object> Input) {
        Log.v(TAG, "set_TextViews : " + Input.values());
        fb_username.setText((String) Input.get("name"));
        fb_useremail.setText((String) Input.get("email"));
        fb_userphone.setText((String) Input.get("phone"));
        fb_usermasterid.setText((String) Input.get("mid"));
    }

    public void set_TextViews(HashMap<String,Object> Input) {
        Log.v(TAG, "set_TextViews : " + Input.values());
        header_name.setText((String) Input.get("name"));
        header_email.setText((String) Input.get("email"));
        if (Input.get("mid").equals("NULL"))
        {
            header_masterid.setText("보유 차량이 없습니다!");
        }
        else {
            header_masterid.setText((String) Input.get("mid"));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        switch(item.getItemId()){
            case R.id.nav_home:
                // 첫 화면
                carfr = new car_func_home();
                fm.beginTransaction().replace(R.id.fragmentlayout, carfr).commitAllowingStateLoss();
                break;

            case R.id.nav_user:
                // 사용자
                userfr = new car_func_user();
                fm.beginTransaction().replace(R.id.fragmentlayout, userfr).commitAllowingStateLoss();
                break;

            case R.id.nav_mycar:
                // 권한 관리
                mycarfr = new car_func_mycar();
                fm.beginTransaction().replace(R.id.fragmentlayout, mycarfr).commitAllowingStateLoss();
                break;

            case R.id.nav_setting:
                // 환경 설정
                setfr = new car_func_setting();
                fm.beginTransaction().replace(R.id.fragmentlayout, setfr).commitAllowingStateLoss();

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
