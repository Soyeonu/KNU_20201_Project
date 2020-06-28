package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import Client_Information.User;

public class car_func_user extends AppCompatActivity {

    TextView myID = findViewById(R.id.my_id);
    TextView myName = findViewById(R.id.my_name);
    TextView myEmail = findViewById(R.id.my_email);
    TextView myPhone = findViewById(R.id.my_phone);
    public User user = new User();
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_func_user);

        myID.setText("My ID~");
        myName.setText("My Name~");
        myEmail.setText("My Email~");
        myPhone.setText("My Phone~");

        Button changePW = findViewById(R.id.changePW_btn);
        Button changeName = findViewById(R.id.changeName_btn);

        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("비밀번호 변경");
                ChangePwDialog pwDialog = new ChangePwDialog(car_func_user.this);
                pwDialog.show();
            }
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("이름 변경");
                ChangeNameDialog nameDialog = new ChangeNameDialog(car_func_user.this);
                nameDialog.show();
            }
        });

        Button ok = findViewById(R.id.user_okBtn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(car_func_user.this, car_func.class);
                startActivity(intent);
            }
        });

        Intent intent;
        String id;
        id = getIntent().getExtras().getString("UserID");
        Get_Profile(id);

        mContext = this;
    }

    public void Get_Profile(String ID) {
        Connection con = new Connection();
        con.setURL("http://ec2-13-124-217-71.ap-northeast-2.compute.amazonaws.com:3000/getuser");
        con.execute(ID);
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

                System.out.println("Type : getUser");
                message.accumulate("ID", strings[0]);
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
            //System.out.println(result);
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                user.setUserID(json.getString("ID"));
                user.setPhone(json.getString("Phone"));
                user.setEmail(json.getString("Email"));
                user.setName(json.getString("Name"));
                user.setOwnMasterID(json.getString("OwnMasterID"));
                Set_Views(user);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Set_Views(User user)
    {
        System.out.println("Username : " + user.getName());
        myID.setText(user.getUserID());
        myName.setText(user.getName());
        myEmail.setText(user.getEmail());
        myPhone.setText(user.getPhone());
    }
}
