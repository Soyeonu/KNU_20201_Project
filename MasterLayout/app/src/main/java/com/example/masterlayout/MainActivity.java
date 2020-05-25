package com.example.masterlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ////////////////////
    private static final String TAG = "MainActivity :";
    private EditText mid_input;
    FirebaseAuth firebaseAuth;
    /////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mid_input = (EditText)findViewById(R.id.mid);

        firebaseAuth = FirebaseAuth.getInstance();
        Button start = findViewById(R.id.btn);
        start.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                login_Anony();//익명 로그인
            }
        });

    }

    public void login_Anony()
    {
        firebaseAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),"인증 완료!",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MasterHome.class);
                    intent.putExtra("mid",mid_input.getText().toString());
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "인증 실패",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    /*

    public void check_mid_listener()        //owner가 존재하는 차량인가?
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_data");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    //Toast.makeText(getApplicationContext(), (String)childDataSnapshot.child("phone").getValue(), Toast.LENGTH_SHORT).show();
                    //Log.v(TAG, "in listener" + childDataSnapshot.getKey()); //displays the key for the node
                    //Log.v(TAG, "in listener" + childDataSnapshot.child("email").getValue());   //gives the value for given keyname
                    if (childDataSnapshot != null)
                    {
                        //og.v(TAG, "in listener" + childDataSnapshot.child("masterid").getKey());
                        Log.v(TAG, "in listener " + childDataSnapshot.child("mid").getValue());   //gives the value for given keyname


                        if(useremail.equals(childDataSnapshot.child("email").getValue()))
                        {
                            HashMap<String,Object> InputMap = new HashMap<>();
                            String email = (String) childDataSnapshot.child("email").getValue();
                            String name = (String) childDataSnapshot.child("name").getValue();
                            String phone = (String) childDataSnapshot.child("phone").getValue();
                            String masterid= (String) childDataSnapshot.child("masterid").getValue();
                            fb_user_profile profile = new fb_user_profile(email,name,phone,masterid);

                            InputMap = (HashMap<String, Object>) profile.toMap();
                            set_invisible_TextViews(InputMap);
                            set_TextViews(InputMap);

                        }


                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }*/



}
