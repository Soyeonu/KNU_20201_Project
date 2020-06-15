package com.example.car_project;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class AuthoritiesDialog extends Dialog{
    private Context context;

    public AuthoritiesDialog(Context context) {
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

        setContentView(R.layout.authorities_dialog);

        // 현재 권한의 상태를 switch로 나타내기 위해 세팅
        final Switch temp_on = findViewById(R.id.temp_on);
        temp_on.setChecked(true);   // 권한이 있으면 true
        final Switch temp_off = findViewById(R.id.temp_off);
        temp_off.setChecked(false); // 권한이 없으면 false

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() { // 권한을 변경하고 저장~
            @Override
            public void onClick(View v) {

                if(temp_on.isChecked()){    // 권한 체크
                    System.out.println("temp_on이 체크되었습니다");
                }

                Toast toast = Toast.makeText(context, "권한 설정이 저장되었습니다", Toast.LENGTH_SHORT);
                toast.show();

                dismiss();
            }
        });
    }

}
