package com.example.car_project;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
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

public class ChangeNameDialog  extends Dialog {    // 알림 활성, 비활성 창

    private Context context;
    private User cur_user;

    public ChangeNameDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.change_name_dialog);

        Button okBtn = findViewById(R.id.changeName_ok);
        Button cancelBtn = findViewById(R.id.changeName_cancel);

        cur_user = ((car_func_user)car_func_user.mContext).user;
        System.out.println("cur_user = " + cur_user);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eName = findViewById(R.id.edit_name);
                System.out.println(eName.getText().toString());

                Change_name(cur_user.getUserID(),eName.getText().toString());
                dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void Change_name(String ID, String NAME) {
        Connection con = new Connection();
        con.setURL("http://ec2-13-124-217-71.ap-northeast-2.compute.amazonaws.com:3000/setname");
        con.execute(ID, NAME);
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

                System.out.println("Type : setname");
                message.accumulate("ID", strings[0]);
                message.accumulate("Name", strings[1]);
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

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"오류발생", Toast.LENGTH_SHORT);
            }
        }
    }
}
