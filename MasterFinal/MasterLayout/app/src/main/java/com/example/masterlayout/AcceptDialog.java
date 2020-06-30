package com.example.masterlayout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AcceptDialog extends Dialog {

    private Context context;
    TextView masterid;
    TextView link;
    private String uid;
    private String mid;
    private String ownerid;

    public AcceptDialog(Context context) {
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

        setContentView(R.layout.accept_dialog);

        masterid = findViewById(R.id.masterId);
        link = findViewById(R.id.link);



        Button okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ok


                dismiss();
            }
        });

        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setinfo(String mid, String uid){
        this.mid = mid;
        this.uid = uid;
    }

    //public void getOwnerid(Stirng ownerid)

    public String getkey(){
        return mid + "_005";
    }

    public void setOwnerID(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Master").child(mid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ownerid = dataSnapshot.child("OwnerID").getValue().toString();

                DatabaseReference regref = FirebaseDatabase.getInstance().getReference("Registration/" + getkey());
                regref.child("RegID").setValue(getkey());
                regref.child("MasterID").setValue(mid);
                regref.child("UserID").setValue(uid);
                regref.child("OwnerID").setValue(ownerid);
                regref.child("ExpireDate").setValue("20/12/31");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
