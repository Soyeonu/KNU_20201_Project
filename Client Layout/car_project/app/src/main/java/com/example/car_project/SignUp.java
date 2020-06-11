package com.example.car_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Client_Information.Registration;
import Client_Information.User;

public class SignUp extends AppCompatActivity {

    private EditText user_id;
    private EditText user_pw;// EditText user_pwd;
    private EditText user_email;
    private EditText user_name;
    private EditText user_phone;
    private EditText master_id;
    private Button signup_btn;

    private FirebaseAuth firebaseAuth;//사용자 인증확인용
    private DatabaseReference Freference;//데베 접근자
    private static final String TAG = "SignUp";

    private static Boolean Error = false;

    private HashMap<String,Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // 회원 가입 창

        user_id = (EditText) findViewById(R.id.id_input);
        user_email = (EditText) findViewById(R.id.email_input);
        user_pw = (EditText) findViewById(R.id.pwd_input);
        user_name = (EditText) findViewById(R.id.name_input);
        user_phone = (EditText) findViewById(R.id.phone_input);

        signup_btn = (Button) findViewById(R.id.sign_up_btn);
     }

    public void Create_User_id(View view) {
        Error = false;
        String id = user_id.getText().toString().trim();
        String pw = user_pw.getText().toString().trim();
        String name = user_name.getText().toString().trim();
        String email = user_email.getText().toString().trim();
        String phone = user_phone.getText().toString().trim();

        // 입력 예외처리
        if (id.equals("") || id == null) {
            Toast.makeText(getApplicationContext(), "ID를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.equals("") || email == null) {
            Toast.makeText(getApplicationContext(), "email을 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pw.equals("") || pw == null) {
            Toast.makeText(getApplicationContext(), "비밀번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        //비밀번호 제약조건 추가
        if (name.equals("") || name == null) {
            Toast.makeText(getApplicationContext(), "이름을 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.equals("") || phone == null) {
            Toast.makeText(getApplicationContext(), "전화번호를 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        Connection con = new Connection();
        con.setURL("http://ec2-13-124-217-71.ap-northeast-2.compute.amazonaws.com:3000/signin");
        con.execute(id, pw, name, phone, email);


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


                System.out.println("Type : Signin");
                message.accumulate("ID", strings[0]);
                message.accumulate("PW", strings[1]);
                message.accumulate("Name", strings[2]);
                message.accumulate("Phone", strings[3]);
                message.accumulate("Email", strings[4]);
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

            if(result.equals("SIGNIN_ERR")){
                Toast.makeText(getApplicationContext(), "회원가입 실패 : 이미 가입된 계정입니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
            }
        }


    }
}