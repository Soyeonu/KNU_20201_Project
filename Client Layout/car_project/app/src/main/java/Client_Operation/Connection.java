package Client_Operation;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... strings){
        try{

            //메시지로 보낼 JSON 객체 생성
            //Connection.execute() 실행 시 () 안에 들어가는 String들을 사용해 객체 생성
            JSONObject message = new JSONObject();
            message.accumulate("target", strings[1]);
            message.accumulate("msg", strings[2]);
            message.accumulate("val", strings[3]);

            //HTTP 연결을 담을 객체 및 버퍼 생성
            HttpURLConnection con = null;
            BufferedReader reader = null;

            try{
                //인자로 들어오는 URL에 연결
                URL url = new URL(strings[0]);
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

}
