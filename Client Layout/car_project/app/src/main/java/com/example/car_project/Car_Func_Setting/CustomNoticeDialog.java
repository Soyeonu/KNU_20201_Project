package com.example.car_project.Car_Func_Setting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.car_project.R;

public class CustomNoticeDialog extends Dialog {    // 알림 활성, 비활성 창

    private Context context;

    public CustomNoticeDialog(Context context) {
        super(context);
        this.context = context;
    }

    //public void callFunction(final TextView main_label) {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_notice_dialog);

        final Switch noticeSwitch = findViewById(R.id.noticeSwitch);
        final Button okBtn = findViewById(R.id.noticeOkBtn);
        final Button cancelBtn = findViewById(R.id.noticeCancelBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 알림 설정 정보 보내기
                if(noticeSwitch.isChecked()) {
                    //알림 활성화 됨
                    Toast toast = Toast.makeText(context, "알림 활성화", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    // 알림 비활성화
                    Toast toast = Toast.makeText(context, "알림 비활성화", Toast.LENGTH_SHORT);
                    toast.show();
                }

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
