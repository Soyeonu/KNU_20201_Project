package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.car_project.Tools.URLManager;

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

public class MainActivity extends AppCompatActivity {
    Button login;
    private EditText eTextId;
    private EditText eTextPw;
    private CheckBox autoLogin;
    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTextId=findViewById(R.id.id);
        eTextPw=findViewById(R.id.pw);
        autoLogin=findViewById(R.id.autoCheck);
        login = findViewById(R.id.loginBtn);



    }

    public void onClickLogin(View v){
        int id = v.getId();
        Intent it;
        switch(id){
            case R.id.loginBtn:
                eTextId = findViewById(R.id.id);
                eTextPw = findViewById(R.id.pw);
                System.out.println("id: = " + eTextId.getText().toString() + " pw: = " + eTextPw.getText().toString());
                Login_Check(eTextId.getText().toString(), eTextPw.getText().toString());

                break;

            case R.id.signupBtn:
                it = new Intent(MainActivity.this, SignUp.class);
                startActivity(it);
        }
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

    public void Login_Check(String ID, final String PW) {
        Connection con = new Connection();
        con.setURL(URLManager.getUrl() + "login");
        con.execute(ID, PW);
    }

    class Connection extends AsyncTask<String, Void, String> {
        private String url;

        protected String doInBackground(String... strings){
            try{
                //Connection.execute() 실행 시 () 안에 들어가는 String들을 사용해 객체 생성
                //메시지 인자에
                System.out.println("connection init");
                System.out.println("URL : " + this.url);

                JSONObject message = new JSONObject();

                System.out.println("Type : Login");
                message.accumulate("ID", strings[0]);
                message.accumulate("PW", strings[1]);
                System.out.println(message.get("ID"));


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

            if(result.equals("ERR_LOGIN")){
                Toast.makeText(getApplicationContext(), "로그인 실패 : 존재하지 않는 아이디거나 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CarSelect.class);
                intent.putExtra("userid", result);
                startActivity(intent);
            }
        }

    }
}

