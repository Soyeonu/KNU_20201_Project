package com.example.car_project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MasterIdDialog extends Dialog {    // 알림 활성, 비활성 창

    private Context context;

    public MasterIdDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.masterid_dialog);

        final Button okBtn = findViewById(R.id.okBtn);
        final Button cancelBtn = findViewById(R.id.cancelBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText masterId = findViewById(R.id.et_masterid);
                System.out.println(masterId.getText().toString());


                dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
