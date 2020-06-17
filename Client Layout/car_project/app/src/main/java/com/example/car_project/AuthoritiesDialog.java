package com.example.car_project;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

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

        final ListView listview;
        final AuthoritiesLVAdapter adapter = new AuthoritiesLVAdapter();

        listview = findViewById(R.id.authority_list);
        listview.setAdapter(adapter);

        // Dialog 눌렀을 때 초기 상태
        // add 권한 이름, 권한 on or off 를 boolean으로 표시 해주시면 됩니당
        adapter.addItem("TEMP_ON", false);
        adapter.addItem("TEMP_OFF", false);
        adapter.addItem("HELLO", false);


        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() { // 권한을 변경하고 저장~
            @Override
            public void onClick(View v) {
                // getFlag로 권한 스위치 온오프 여부를 알 수 있습니다
                for(int i = 0; i < adapter.getCount(); i++){
                   System.out.println( ((AuthoritiesListViewItem)adapter.getItem(i)).getFlag());
                }

                Toast toast = Toast.makeText(context, "권한 설정이 저장되었습니다", Toast.LENGTH_SHORT);
                toast.show();

                dismiss();
            }
        });
    }

}
