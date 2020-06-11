package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignIn extends AppCompatActivity {

    private EditText eTextId;
    private EditText eTextPw;
    private CheckBox autoLogin;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference Freference;
    private static final String TAG = "SignIn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        Freference = FirebaseDatabase.getInstance().getReference();
        //test_listener();
    }
/*
    public void onStart(){
        super.onStart();
        //유저가 이미 접속중인지 확인(추가?)
        FirebaseUser current_user = firebaseAuth.getCurrentUser();
        if (current_user == null) {
            // User is signed in.
            String email = current_user.getEmail();
            Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
            //firebaseAuth.signOut();
        } else {
            // No user is signed in.
            Toast.makeText(getApplicationContext(), "not any one", Toast.LENGTH_SHORT).show();
        }

    }*/

    public void onClickLogin(View v){
        int id = v.getId();
        //Intent it;
        String id_input = null;
        String pwd_input = null;
        final String TAG = "SignIn";

        switch(id){
            case R.id.loginBtn:
                //입력받은 ID, PW 받아옴
                eTextId = findViewById(R.id.id);
                eTextPw = findViewById(R.id.pw);
                id_input = eTextId.getText().toString().trim();
                pwd_input = eTextPw.getText().toString().trim();

                //로그인 예외처리
                if(id_input.equals("") || id_input == null)
                {
                    Toast.makeText(getApplicationContext(), "ID를 채워주세요!", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(pwd_input.equals("") || pwd_input == null)
                {
                    Toast.makeText(getApplicationContext(), "비밀번호를 채워주세요!", Toast.LENGTH_SHORT).show();
                    break;
                }

                System.out.println("id: = " + eTextId.getText().toString() + " pw: = " + eTextPw.getText().toString());

                Login_Check(eTextId.getText().toString(), eTextPw.getText().toString());

                break;
        }
    }

    public void Login_Check(String ID, final String PW) {
        Connection con = new Connection();
        con.setURL("http://ec2-13-124-217-71.ap-northeast-2.compute.amazonaws.com:3000/login");
        con.execute(ID, PW);
    }

    class Connection extends AsyncTask<String, Void, String> {
        private String URL;

        protected String doInBackground(String... strings){
            try{
                //Connection.execute() 실행 시 () 안에 들어가는 String들을 사용해 객체 생성
                //메시지 인자에
                System.out.println("connection init");
                System.out.println("URL : " + this.URL);

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
                    java.net.URL url = new URL(this.URL);
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

        public void setURL(String url) { this.URL = url; }

        public void onPostExecute(String result){
            System.out.println(result);

            if(result.equals("ERR_LOGIN")){
                Toast.makeText(getApplicationContext(), "로그인 실패 : 존재하지 않는 아이디거나 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), car_func.class);
                intent.putExtra("userid", result);
                startActivity(intent);
            }
        }

    }
}
