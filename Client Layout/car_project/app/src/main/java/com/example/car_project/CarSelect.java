package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.car_project.Tools.URLManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CarSelect extends AppCompatActivity {
    private Toast toast;
    private long backKeyPressedTime = 0;    // 뒤로가기 버튼 시간

    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_select);

        //final ListView listview;
        //final CarSelectLVAdapter adapter = new CarSelectLVAdapter();

        Intent intent = getIntent();
        uid = intent.getExtras().getString("userid");


        //listview = findViewById(R.id.carSelect_listview);
        //listview.setAdapter(adapter);

        // adapter에 addItem( masterId, Owner name) 으로 리스트 뷰에 추가할 수 있습니다
        getRegListFromServer(uid);
        //adapter.addItem("hello", "길동");
        //adapter.addItem("no", "아니");
        //adapter.addItem("bye", "잘가");

        // 리스트뷰 클릭 리스너
        /*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // adapter.getItem(position)으로 listview안의 값을 가져 올 수 있음.
                // 클릭한 리스트뷰 마스터 아이디 출력
                System.out.println(((CarSelectListView)adapter.getItem(position)).getMasterId());

                // Car func 액티비티로 이동
                Intent intent = new Intent(CarSelect.this, car_func.class);
                startActivity(intent);
            }
        });

         */
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

    public void getRegListFromServer(String uid){
        Connection con = new Connection();
        con.setURL(URLManager.getUrl() + "getreg");
        con.execute(uid);
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

                System.out.println("Type : getReg");
                message.accumulate("UserID", strings[0]);
                System.out.println(message.get("UserID"));


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

            if(result.equals("NO_REG")){
                Toast.makeText(getApplicationContext(), "현재 등록된 차량이 없습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                final ListView listview;
                final CarSelectLVAdapter adapter = new CarSelectLVAdapter();

                listview = findViewById(R.id.carSelect_listview);
                listview.setAdapter(adapter);

                try {
                    JSONObject json = new JSONObject(result);
                    int count = json.getInt("count");
                    if(count == 0){
                        //등록된 차량이 없을 시
                        //이곳에 버튼을 선언하고 띄운 후 클릭 리스너도 만들어주면 됨

                        return;
                    }

                    for(int i = 0; i < count; i++){
                        String master = json.getString("master"+Integer.toString(i));
                        String owner = json.getString("owner" + Integer.toString(i));
                        String regid = json.getString("reg" + Integer.toString(i));
                        String exp = json.getString("exp" + Integer.toString(i));

                        System.out.println("master : " + master + "/ owner : " + owner);
                        adapter.addItem(master, owner, regid, exp);
                    }

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            // adapter.getItem(position)으로 listview안의 값을 가져 올 수 있음.
                            // 클릭한 리스트뷰 마스터 아이디 출력
                            System.out.println(((CarSelectListView)adapter.getItem(position)).getMasterId());

                            //Toast.makeText(getApplicationContext(), ((CarSelectListView)adapter.getItem(position)).getMasterId() + "리스너 구동", Toast.LENGTH_SHORT).show();
                            // Car func 액티비티로 이동
                            Intent intent = new Intent(CarSelect.this, car_func.class);
                            intent.putExtra("userid", uid);
                            String masterID = ((CarSelectListView) adapter.getItem(position)).getMasterId();
                            String regID = ((CarSelectListView) adapter.getItem(position)).getRegid();
                            String exp = ((CarSelectListView) adapter.getItem(position)).getExpdate();

                            intent.putExtra("masterid", masterID);
                            intent.putExtra("regid", regID);
                            intent.putExtra("expdate", exp);

                            startActivity(intent);
                        }
                    });

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }

    }
}
