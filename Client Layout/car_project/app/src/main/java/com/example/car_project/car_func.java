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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Client_Information.User;

public class car_func extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navView;
    private static final String TAG = "car_func";

    private FragmentManager fm;
    private FragmentTransaction ft;
    car_func_home carfr;
    car_func_setting setfr;
    car_func_user userfr;
    car_func_mycar mycarfr;

    /////////////////
    FirebaseAuth firebaseAuth;//사용자 인증확인용
    DatabaseReference Freference;//데이터베이스 접근용

    TextView header_name;
    TextView header_email;
    TextView header_masterid;

    private User user = new User();
    //////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_func);

        Intent intent = getIntent();
        String UserID = (String)intent.getSerializableExtra("userid");
        Toast.makeText(getApplicationContext(), UserID, Toast.LENGTH_SHORT).show();

        car_func_home frHome = new car_func_home();
        Bundle bundle = new Bundle(1);
        //bundle.putString("masterid",masterid);
        frHome.setArguments(bundle);
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

        View header = navView.getHeaderView(0);
        header_name = (TextView)header.findViewById(R.id.user_name);
        header_email = (TextView)header.findViewById(R.id.user_email);


        //Firebase 인증 객체
        firebaseAuth = FirebaseAuth.getInstance();
        Freference = FirebaseDatabase.getInstance().getReference();

        //로그인을 통해 획득한 유저 정보가 담길 객체
        user = new User();

        Log.v(TAG, "oncreate userid : " + UserID);

        //유저 정보 담기
        set_User(UserID);
    }

    protected void onStart() {
        super.onStart();
        login_check();
    }

    public void login_check()
    {
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignIn.class));
        }
        //유저가 있다면, null이 아니면 계속 진행
        //Toast.makeText(getApplicationContext(),"반갑습니다.\n"+ masterid+"에 "+ userid +"으로 로그인 하였습니다.", Toast.LENGTH_SHORT).show();
    }

    public Boolean is_available()
    {
        /*
           0. 마스터 아이디 존재여부
           1. 기한체크 expired
           2. 오너체크 0==0
           3. 권한체크 perm == null
        */
        return true;
    }

    public void set_User(String UID) {
        //현재 접속 유저의 정보를 가져오는 리스너
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");
        ref.orderByChild("UserID").equalTo(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childSanpshot : dataSnapshot.getChildren()){
                    user.setUserID(childSanpshot.child("UserID").getValue(String.class));
                    user.setUserPW(childSanpshot.child("UserPW").getValue(String.class));
                    user.setName(childSanpshot.child("Name").getValue(String.class));
                    user.setPhone(childSanpshot.child("Phone").getValue(String.class));
                    user.setEmail(childSanpshot.child("Email").getValue(String.class));
                }

                set_TextViews();    //헤더 textview 설정
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void set_TextViews()
    {
        header_name.setText(user.getName());
        header_email.setText(user.getEmail());
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
