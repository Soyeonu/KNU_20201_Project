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
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class car_func extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navView;

    private FragmentManager fm;
    private FragmentTransaction ft;
    car_func_home carfr;
    car_func_setting setfr;
    car_func_mycar mycarfr;

    private Toast toast;
    private long backKeyPressedTime = 0;
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
                Intent intent = new Intent(this, Car_func_user.class);
                startActivity(intent);

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

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼 기능
        //  super.onBackPressed();

        if(System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\'버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        // 2초 이내로 다시 뒤로가기 버튼 누르면 toast 취소
        // 표시된 toast 취소
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
            toast.cancel();
        }
    }

}
