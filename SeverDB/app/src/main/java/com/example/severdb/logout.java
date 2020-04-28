package com.example.severdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// 이 class는 자동 로그아웃 테스트 클래스 입니다.
// logout 버튼을 누른 후
// 앱을 종료하고 다시 실행했을 때,
// 자동로그인이 풀려있습니다.
public class logout extends AppCompatActivity {

    Button button;
    TextView textView;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        //Main에서 만들어둔 preference인 mine을 자유롭게 사용할 수 있다는 것을 보여줌
        pref = getSharedPreferences("mine",MODE_PRIVATE);
        editor = pref.edit();
        textView = findViewById(R.id.logininfo);

        //로그인 중이라면 자동로그인 유무 상관 없이
        //preference 안에 있는 id, pwd를 가져와서 출력
        String s;
        s = "ID : " + pref.getString("ID","") + "\n PWD : " + pref.getString("PWD","");
        textView.setText(s);


        //로그아웃을 선택하면
        //자동 로그인 설정이 해지됨
        //preference의 내용을 전부 없앰
        // Main으로 돌아감
        button=findViewById(R.id.logoutbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                finish();
            }
        });
    }
}
