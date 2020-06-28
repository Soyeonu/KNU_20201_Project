package com.example.car_project;

        import android.app.Dialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.Switch;
        import android.widget.TextView;
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
        import java.util.List;

        import Client_Information.User;

public class AirconDialog extends Dialog {
    private Context context;
    TextView tv_temp;   // textview로 나타낸 온도
    EditText et_temp;   // edittext로 입력받는 온도

    public AirconDialog(Context context) {
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

        setContentView(R.layout.aircon_dialog);

        tv_temp = findViewById(R.id.tv_temperature);
        et_temp = findViewById(R.id.input_temp);

        //현재 설정된 온도를 받기 위해 TEMP_CHK 메시지 전송
        Do_Function("TEMP_CHK", "RES", null);

        TempBtnClickListener btnClickListener = new TempBtnClickListener();
        Button temp_up = findViewById(R.id.temp_upBtn);
        Button temp_down = findViewById(R.id.temp_downBtn);
        temp_up.setOnClickListener(btnClickListener);
        temp_down.setOnClickListener(btnClickListener);

        Button saveBtn = findViewById(R.id.airconOkBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = et_temp.getText().toString();
                String msg = val + "도까지 설정합니다";
                Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.show();

                Do_Function("TEMP_DST", "NORES", val);
                //RES Type : Master에게서 응답을 받아야 하는 경우
                //NORES Type : Master에게 메시지를 보낸 후 별도 응답이 필요없는 경우

                dismiss();
            }
        });
    }

    class TempBtnClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.temp_upBtn:
                    int temp =  Integer.parseInt(tv_temp.getText().toString());
                    temp += 1;
                    String str_temp = Integer.toString(temp);
                    System.out.println(str_temp);
                    tv_temp.setText(str_temp);
                    et_temp.setText(str_temp);
                    break;
                case R.id.temp_downBtn:
                    temp =  Integer.parseInt(tv_temp.getText().toString());
                    temp -= 1;
                    str_temp = Integer.toString(temp);
                    System.out.println(str_temp);
                    tv_temp.setText(str_temp);
                    et_temp.setText(str_temp);
                    break;
            }
        }
    }

    public void Do_Function(String Message, String type, String value) {
        System.out.println("Do : " + Message);
        Connection con = new Connection();
        con.setURL(URLManager.getUrl() + "sendmsgfromuser");
        User user = new User();
        con.execute(user.getRegistration().getRegID(), user.getRegistration().getMasterID(),
                user.getUserID(), Message, type, value);
    }

    class Connection extends AsyncTask<String, Void, String> {
        private String url;
        private Boolean RESMODE = false;

        protected String doInBackground(String... strings){
            try{
                //Connection.execute() 실행 시 () 안에 들어가는 String들을 사용해 객체 생성
                //메시지 인자에
                System.out.println("connection init");
                System.out.println("URL : " + this.url);

                JSONObject message = new JSONObject();

                //System.out.println("Type : Login");
                message.accumulate("RegID", strings[0]);
                message.accumulate("MasterID", strings[1]);
                message.accumulate("UserID", strings[2]);
                message.accumulate("Msg", strings[3]);
                message.accumulate("Type", strings[4]);
                if(strings[4].equals("RES")) RESMODE = true;

                if(strings[5] != null) message.accumulate("Val", Integer.parseInt(strings[5]));


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
                    java.net.URL redir = new URL(URLManager.getUrl() + "waitfor?mid=" + strings[1] + "&type=TEMP");
                    if(RESMODE) con = (HttpURLConnection)redir.openConnection();

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

            if(!RESMODE) {
                if (result.equals("MSG SEND COMPLETE")) {
                    Toast toast = Toast.makeText(context, "명령 전달 완료", Toast.LENGTH_SHORT);
                } else {
                    //Error Handle
                    Toast toast = Toast.makeText(context, "명령 실행 도중 에러가 발생했습니다 : " + result, Toast.LENGTH_SHORT);
                }
            }
            else{
                TextView tv_temp;   // textview로 나타낸 온도
                tv_temp = findViewById(R.id.tv_temperature);
                tv_temp.setText(result);
            }
        }

    }

}
