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

import Client_Information.User;
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
    DatabaseReference Freference;//데이터베이스 접근용
    String username;
    String useremail;
    String userid;

    TextView header_name;
    TextView header_email;
    TextView header_masterid;

    //////////////////
    private User user;


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

        Intent intent = getIntent();
        userid = intent.getExtras().getString("uid");
        Log.v(TAG, "oncreate userid : " + userid);

        firebaseAuth = FirebaseAuth.getInstance();
        Freference = FirebaseDatabase.getInstance().getReference();
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignIn.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser fbuser = firebaseAuth.getCurrentUser();
        Toast.makeText(getApplicationContext(),"반갑습니다.\n"+ userid +"으로 로그인 하였습니다.", Toast.LENGTH_SHORT).show();

        View header = navView.getHeaderView(0);
        header_name = (TextView)header.findViewById(R.id.user_name);
        header_email = (TextView)header.findViewById(R.id.user_email);

        user = new User();

        fb_userdata_listener();     //사용자 정보를 업데이트를 위한 리스너
        //fb_masterinfo_listener();     //마스터 정보를 받기 위한 리스너
    }

    public void fb_userdata_listener() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_data").child(userid);
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
                    user = userinfo;
                    Log.v(TAG," + " + userinfo);

                    set_TextViews();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
/*
    public void set_fb_TextViews(HashMap<String,Object> Input) {
        Log.v(TAG, "set_TextViews : " + Input.values());

        fb_userid.setText((String)Input.get("id"));
        fb_userpwd.setText((String)Input.get("pwd"));
        fb_username.setText((String) Input.get("name"));
        fb_useremail.setText((String) Input.get("email"));
        fb_userphone.setText((String) Input.get("phone"));
    }
    */

    public void set_TextViews()
    {
        Log.v(TAG,"setTextview - username : " + user.get_profile().get_username());
        header_name.setText(user.get_profile().get_username());
        header_email.setText(user.get_profile().get_email());
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
