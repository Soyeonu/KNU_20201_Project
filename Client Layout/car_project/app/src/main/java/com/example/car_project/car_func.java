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
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car_project.Tools.GlobalManager;
import com.example.car_project.Tools.URLManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import Client_Information.User;

public class car_func extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navView;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private String userid;
    private String regid;

    private User user = new User();

    car_func_home carfr;
    car_func_setting setfr;
    car_func_user userfr;
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

        //로그인 정보를 통해 사용자 정보 가져오기
        Intent intent = getIntent();
        userid = intent.getExtras().getString("userid");
        regid = intent.getExtras().getString("regid");

        user.setRegID(regid);

        getUserInfoFromServer(userid);


    }

    public User getUser() { return this.user; }

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
                Intent intent = new Intent(car_func.this, car_func_user.class);
                startActivity(intent);
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

    public void getUserInfoFromServer(String userid){
        Connection con = new Connection();
        con.setURL(URLManager.getUrl() + "getuser");
        con.execute(userid);
    }

    public String getUserid() { return this.userid; }

    class Connection extends AsyncTask<String, Void, String> {
        private String url;

        protected String doInBackground(String... strings){
            try{
                //Connection.execute() 실행 시 () 안에 들어가는 String들을 사용해 객체 생성
                //메시지 인자에
                System.out.println("connection init");
                System.out.println("URL : " + this.url);

                JSONObject message = new JSONObject();


                System.out.println("Type : getUser");
                message.accumulate("ID", strings[0]);

                //System.out.println(message.get("ID"));


                //HTTP 연결을 담을 객체 및 버퍼 생성
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //인자로 들어오는 URL에 연결
                    java.net.URL url = new URL(this.url);
                    con = (HttpURLConnection)url.openConnection();

                    //연결 설정
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "text/html");

                    OutputStream outputStream = con.getOutputStream();

                    //버퍼에 message를 담아 전송
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                    writer.write(message.toString());
                    writer.flush();
                    writer.close();

                    //응답을 수신
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    System.out.println("result : " + buffer.toString());
                    return buffer.toString();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        public void setURL(String url) { this.url = url; }

        public void onPostExecute(String result){
            System.out.println(result);

            if(result.equals("GETUSER_ERR")){
                Toast.makeText(getApplicationContext(), "오류 발생 : 유저 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                try {
                    JSONObject json = new JSONObject(result);
                    user.setUserID(json.getString("ID"));
                    user.setPhone(json.getString("Phone"));
                    user.setEmail(json.getString("Email"));
                    user.setName(json.getString("Name"));
                    user.setOwnMasterID(json.getString("OwnMasterID"));

                    GlobalManager globalManager = (GlobalManager)getApplicationContext();
                    globalManager.setState(user);

                    //drawer header의 내용 변경
                    TextView drawheader_Name = (TextView) findViewById(R.id.user_name);
                    TextView drawheader_Email = (TextView) findViewById(R.id.user_email);

                    drawheader_Name.setText(user.getName());
                    drawheader_Email.setText(user.getEmail());
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "반갑습니다", Toast.LENGTH_SHORT).show();
            }
        }


    }

}
