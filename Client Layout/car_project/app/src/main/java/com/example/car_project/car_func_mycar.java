package com.example.car_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class car_func_mycar extends Fragment {
    View view;
    private String mid;

    public car_func_mycar() {
        // Required empty public constructor
        // 내 차량 관리
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_car_func_mycar, container, false);

        mid = ((car_func) getActivity()).getUser().getOwnMasterID();
        getRegUserList(mid);

        return view;
    }

    public void getRegUserList(String mid){
        Connection con = new Connection();
        con.setURL(URLManager.getUrl() + "getreguser");
        con.execute(mid);
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

                message.accumulate("MasterID", strings[0]);

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

            if(result.equals("NO_REGUSER")){

            }
            else{
                final ListView listview;
                final ListViewAdapter adapter = new ListViewAdapter();

                listview = view.findViewById(R.id.user_listview);
                listview.setAdapter(adapter);

                try {
                    JSONObject json = new JSONObject(result);
                    int count = json.getInt("count");
                    for(int i = 0; i < count; i++){
                        String userid = json.getString("UserID"+Integer.toString(i));
                        String expdate = json.getString("ExpDate" + Integer.toString(i));
                        String regid = json.getString("RegID" + Integer.toString(i));

                        adapter.addItem(userid, expdate, regid);
                    }

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            AuthoritiesDialog dialog = new AuthoritiesDialog(getContext());
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = 800;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                            System.out.println(((ListViewItem)adapter.getItem(position)).getRegID());
                            dialog.setRegID(((ListViewItem)adapter.getItem(position)).getRegID());
                            dialog.setMasterID(mid);
                            dialog.show();
                            Window window = dialog.getWindow();
                            window.setAttributes(lp);
                        }
                    });

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }

    }


}
