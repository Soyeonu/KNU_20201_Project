package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    EditText idedit;
    EditText pwdedit;
    EditText phoneedit;

    String id, pwd, phone;
    Button registerbtn;
    DbOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // 회원 가입 창
        dbOpenHelper = new DbOpenHelper(this);
        idedit = findViewById(R.id.editID);
        pwdedit = findViewById(R.id.editPWD);
        phoneedit = findViewById(R.id.editPhonenum);
        registerbtn = findViewById(R.id.doSignUp);
    }
    private void getEdittext() {

        id = idedit.getText().toString();
        pwd = pwdedit.getText().toString();
        phone = phoneedit.getText().toString();
    }
    Button.OnClickListener onClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.doSignUp:
                    getEdittext();

                    dbOpenHelper.insert(id, pwd, phone, "");
                    dbOpenHelper.getAllData();
                    dbOpenHelper.close();
                    break;
            }

        }
    };
}
