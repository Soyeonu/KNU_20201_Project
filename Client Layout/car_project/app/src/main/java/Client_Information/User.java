package Client_Information;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


public class User implements Serializable {
    private String UserID;
    private String UserPW;
    private String Name;
    private String Phone;
    private String Email;

    //Empty Constructor For Firebase
    public User()
    {    }

    public User(String ID, String PW, String Name, String Phone, String Email){
        this.UserID = ID;
        this.UserPW = PW;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
    }


    public String getUserID(){ return UserID; }
    public String getUserPW(){ return UserPW; }
    public String getName(){ return Name; }
    public String getPhone(){ return Phone; }
    public String getEmail(){ return Email; }


    public void setEmail(String email) {
        Email = email;
    }
    public void setUserID(String userID) { UserID = userID; }
    public void setUserPW(String userPW) {
        UserPW = userPW;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

}
