package Client_Information;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


public class User implements Serializable {
    private static String UserID;
    private static String Name;
    private static String Phone;
    private static String Email;

    //Empty Constructor For Firebase
    public User()
    {    }

    //보안을 위해 생성자에 PW는 없음.
    public User(String ID, String Name, String Phone, String Email){
        this.UserID = ID;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
    }


    public String getUserID(){ return UserID; }
    public String getName(){ return Name; }
    public String getPhone(){ return Phone; }
    public String getEmail(){ return Email; }


    public void setEmail(String email) {
        Email = email;
    }
    public void setUserID(String userID) { UserID = userID; }
    public void setName(String name) {
        Name = name;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

}
