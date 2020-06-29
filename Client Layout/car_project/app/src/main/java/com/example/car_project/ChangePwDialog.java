package com.example.car_project;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class ChangePwDialog extends Dialog {    // 알림 활성, 비활성 창

    private Context context;

    public ChangePwDialog(Context context) {
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

        setContentView(R.layout.change_pw_dialog);

        Button okBtn = findViewById(R.id.changePw_ok);
        Button cancelBtn = findViewById(R.id.changePw_cancel);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ePw = findViewById(R.id.edit_pw);
                EditText ePwCheck = findViewById(R.id.edit_pw_check);

                if(!(ePw.getText().toString()).equals(ePwCheck.getText().toString())) {
                    Toast toast = Toast.makeText(getContext(),"비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    // 비밀번호가 일치하면 변경된 비밀번호 적용해주면 됩니당

                    dismiss();
                }
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
