package com.example.car_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/*
초안 제작용 테스트 클래스

mid를 입력받아 master_login -> login_bit를 확인 1=접속 -> car_func로 진행
미접속 시 토스트
접속 시 권한 검사는
 */

public class mid_select  extends AppCompatActivity {

    TextView login_bit;
    TextView mid;

    ////////////////////////////
    TextView fb_bit;
    TextView fb_mid;

    EditText mid_input;
    Button connect_btn;

    HashMap<String,Object> mid_hashmap;
    Intent in_intent;
    private static final String TAG = "mid_select";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_select);

        login_bit = (TextView)findViewById(R.id.login_view);
        mid = (TextView)findViewById(R.id.mid_view);

        fb_bit =(TextView)findViewById(R.id.invisible_login);
        fb_mid = (TextView)findViewById(R.id.invisible_mid);

        mid_input =(EditText)findViewById(R.id.mid_input);
        connect_btn = (Button)findViewById(R.id.connect_button);

        mid_hashmap = new HashMap<>();
        in_intent = getIntent();

        Log.v(TAG,"oncreate_uid: "+in_intent.getExtras().getString("uid"));

        fb_login_listener();        //마스터의 로그인 확인을 위한 리스너
    }

    public void fb_login_listener()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("master_info");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                {
                    //Log.v(TAG, "in listener" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v(TAG, "in listener" + childDataSnapshot.child("email").getValue());   //gives the value for given keyname
                    String mid = (String) childDataSnapshot.child("mid").getValue();
                    String bit = (String) childDataSnapshot.child("login_bit").getValue();
                    Log.v(TAG, "+ "+bit);

                   create_midmap(mid,bit);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public void create_midmap(String Mid, String Bit)       //접속한 모든 마스터 아이디의 맵 생성
    {
        //settext
        fb_mid.setText(Mid);
        fb_bit.setText(Bit);

        String mid = fb_mid.getText().toString();
        String bitstring = fb_bit.getText().toString();
        Log.v(TAG, "map : "+mid +" "+bitstring);

        mid_hashmap.put(mid,bitstring);
        Log.d(TAG, "map method : "+mid_hashmap);
    }

    public void connect_next(View v)        //다음 액티비티로 진행하기 위해 리스너 통해 만든 맵을 검색
    {   //해당차량이 db에 존재하는가?
        String input = mid_input.getText().toString();
        String bit = (String) mid_hashmap.get(input);
        if( mid_hashmap.containsKey(input))
        {
            //해당 차량이 접속중인가?
            if (bit.equals("1") )
            {
                Intent intent = new Intent(mid_select.this, car_func.class);
                intent.putExtra("uid",in_intent.getExtras().getString("uid"));
                intent.putExtra("mid", mid_input.getText().toString());
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "해당 차량은 미접속 상태입니다!", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "해당 차량은 존재하지 않네요!", Toast.LENGTH_SHORT).show();
        }
        mid.setText(input);
        login_bit.setText(bit);
        return ;
    }
}
