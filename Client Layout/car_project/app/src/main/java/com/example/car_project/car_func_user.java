package com.example.car_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import Client_Information.User;

public class car_func_user extends AppCompatActivity {

    public User user = new User();
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_func_user);

        TextView myID = findViewById(R.id.my_id);
        TextView myName = findViewById(R.id.my_name);
        TextView myEmail = findViewById(R.id.my_email);
        TextView myPhone = findViewById(R.id.my_phone);

        User user = new User();

        myID.setText(user.getUserID());
        myName.setText(user.getName());
        myEmail.setText(user.getEmail());
        myPhone.setText(user.getPhone());

        Button changePW = findViewById(R.id.changePW_btn);
        Button changeName = findViewById(R.id.changeName_btn);

        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("비밀번호 변경");
                ChangePwDialog pwDialog = new ChangePwDialog(car_func_user.this);
                pwDialog.show();
            }
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("이름 변경");
                ChangeNameDialog nameDialog = new ChangeNameDialog(car_func_user.this);
                nameDialog.show();
            }
        });

        Intent intent;
        String id;
        id = getIntent().getExtras().getString("UserID");

        mContext = this;
    }
}
