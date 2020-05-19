package com.example.severdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ViewSwitcher;

public class LoginRegister extends AppCompatActivity {

    EditText idedit;
    EditText pwdedit;
    EditText phoneedit;


    String id, pwd, phone;
    String authority;

    Button registerbtn;

    RadioButton owner;
    RadioButton teporary;
    RadioButton privatec;
    RadioButton publicc;

    DbOpenHelper dbOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);


        dbOpenHelper = new DbOpenHelper(this);
        idedit = findViewById(R.id.editID);
        pwdedit = findViewById(R.id.editPWD);
        phoneedit = findViewById(R.id.editPhonenum);
        registerbtn = findViewById(R.id.registerok);
        owner = findViewById(R.id.ownerc);
        teporary = findViewById(R.id.temporaryc);
        privatec = findViewById(R.id.privatec);
        publicc = findViewById(R.id.publicc);

        registerbtn.setOnClickListener(onClickListener);
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
                case R.id.registerok:
                    getEdittext();

                    if (owner.isChecked() == true) authority = "Owner";
                    if (teporary.isChecked() == true) authority = "Temporary";
                    if (privatec.isChecked() == true) authority = "Private";
                    if (publicc.isChecked() == true) authority = "Public";

                    dbOpenHelper.insert(id, pwd, phone, authority);
                    dbOpenHelper.getAllData();
                    finish();
                    break;
            }

        }
    };
}
