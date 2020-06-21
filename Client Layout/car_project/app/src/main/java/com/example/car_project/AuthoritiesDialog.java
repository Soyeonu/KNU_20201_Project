package com.example.car_project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.car_project.Tools.GlobalManager;
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
import java.util.Iterator;
import java.util.List;

public class AuthoritiesDialog extends Dialog{
    private Context context;
    private String RegID;
    private String MasterID;

    public AuthoritiesDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.authorities_dialog);

        final ListView listview;
        final AuthoritiesLVAdapter adapter = new AuthoritiesLVAdapter();

        listview = findViewById(R.id.authority_list);
        listview.setAdapter(adapter);

        getPermissionFromServer(RegID, MasterID);
    }

    public void getPermissionFromServer(String regid, String mid){
        Connection_get con = new Connection_get();
        con.setURL(URLManager.getUrl() + "getperms");
        con.execute(regid, mid);
    }

    public void setRegID(String regid) { this.RegID = regid; }
    public void setMasterID(String mid) { this.MasterID = mid; }


    class Connection_get extends AsyncTask<String, Void, String> {
        private String url;

        protected String doInBackground(String... strings){
            try{
                //Connection.execute() 실행 시 () 안에 들어가는 String들을 사용해 객체 생성
                //메시지 인자에
                System.out.println("connection init");
                System.out.println("URL : " + this.url);

                JSONObject message = new JSONObject();

                System.out.println("Type : getReg");
                message.accumulate("RegID", strings[0]);
                message.accumulate("MasterID", strings[1]);

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
            System.out.println("res from server : " + result);
            final ListView listview;
            final AuthoritiesLVAdapter adapter = new AuthoritiesLVAdapter();

            listview = findViewById(R.id.authority_list);
            listview.setAdapter(adapter);

            if(result.equals("NO_PERM")){
                Toast.makeText(context.getApplicationContext(), "로그인 실패 : 존재하지 않는 아이디거나 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                try {
                    JSONObject json = new JSONObject(result);
                    Iterator i = json.keys();
                    System.out.println(json.keys().toString());
                    do{
                        String permID = i.next().toString();
                        System.out.println("PermID is : " + permID);
                        if(permID.contains("_name")) continue;
                        Boolean bool = json.getBoolean(permID);
                        String permName = json.getString(permID + "_name");
                        adapter.addItem(permName, bool);
                    }while(i.hasNext());

                }catch(Exception e){
                    e.printStackTrace();
                }

                Button saveBtn = findViewById(R.id.saveBtn);
                saveBtn.setOnClickListener(new View.OnClickListener() { // 권한을 변경하고 저장~
                    @Override
                    public void onClick(View v) {
                        // getFlag로 권한 스위치 온오프 여부를 알 수 있습니다
                        for(int i = 0; i < adapter.getCount(); i++){
                            System.out.println( ((AuthoritiesListViewItem)adapter.getItem(i)).getFlag());
                        }

                        Toast toast = Toast.makeText(context, "권한 설정이 저장되었습니다", Toast.LENGTH_SHORT);
                        toast.show();

                        dismiss();
                    }
                });
            }
        }

    }

    class Connection_set extends AsyncTask<JSONObject, Void, String> {
        private String url;

        protected String doInBackground(JSONObject... json){
            try{
                //Connection.execute() 실행 시 () 안에 들어가는 String들을 사용해 객체 생성
                //메시지 인자에
                System.out.println("connection init");
                System.out.println("URL : " + this.url);

                JSONObject message = json[0];

                System.out.println("Type : getReg");
                //message.accumulate("RegID", strings[0]);

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
            System.out.println("res from server : " + result);

            if(result.equals("NO_PERM")){
                Toast.makeText(context.getApplicationContext(), "로그인 실패 : 존재하지 않는 아이디거나 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                final ListView listview;
                final AuthoritiesLVAdapter adapter = new AuthoritiesLVAdapter();

                listview = findViewById(R.id.carSelect_listview);
                listview.setAdapter(adapter);
            }
        }

    }
}
