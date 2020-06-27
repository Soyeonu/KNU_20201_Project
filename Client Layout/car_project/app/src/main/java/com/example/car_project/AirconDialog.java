package com.example.car_project;

        import android.app.Dialog;
        import android.content.Context;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.List;

public class AirconDialog extends Dialog {
    private Context context;
    TextView tv_temp;   // textview로 나타낸 온도
    EditText et_temp;   // edittext로 입력받는 온도

    public AirconDialog(Context context) {
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

        setContentView(R.layout.aircon_dialog);

        tv_temp = findViewById(R.id.tv_temperature);
        tv_temp.setText("27");
        et_temp = findViewById(R.id.input_temp);

        TempBtnClickListener btnClickListener = new TempBtnClickListener();
        Button temp_up = findViewById(R.id.temp_upBtn);
        Button temp_down = findViewById(R.id.temp_downBtn);
        temp_up.setOnClickListener(btnClickListener);
        temp_down.setOnClickListener(btnClickListener);

        Button saveBtn = findViewById(R.id.airconOkBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et_temp.getText().toString();
                str = str + "도까지 설정합니다";
                Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
                toast.show();

                dismiss();
            }
        });
    }

    class TempBtnClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.temp_upBtn:
                    int temp =  Integer.parseInt(tv_temp.getText().toString());
                    temp += 1;
                    String str_temp = Integer.toString(temp);
                    System.out.println(str_temp);
                    tv_temp.setText(str_temp);
                    et_temp.setText(str_temp);
                    break;
                case R.id.temp_downBtn:
                    temp =  Integer.parseInt(tv_temp.getText().toString());
                    temp -= 1;
                    str_temp = Integer.toString(temp);
                    System.out.println(str_temp);
                    tv_temp.setText(str_temp);
                    et_temp.setText(str_temp);
                    break;
            }
        }
    }

}
