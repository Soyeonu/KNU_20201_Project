package Client_Information;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Calendar;

import FB_obj.fb_user_profile;

public class User {
    public Profile UserProfile;
    public ArrayList<Registration> RegList= new ArrayList<Registration>();
    public String UserID;
    public String UserPW;

    public User()
    {    }

    public User(String ID, String PW,Profile prof){
        this.UserID = ID;
        this.UserPW = PW;
        this.UserProfile = prof;
    }

    public User(String ID, String PW,Profile prof,ArrayList<Registration> reg){
        this.UserID = ID;
        this.UserPW = PW;
        this.UserProfile = prof;
        this.RegList = reg;
    }

    public void Register(String MasterID, String Expiredate){
        Registration NewReg = new Registration(MasterID, Expiredate);
        this.RegList.add(NewReg);
    }

    public String get_userid()
    {
        return this.UserID;
    }

    public Profile get_profile()
    {
        return this.UserProfile;
    }

    public boolean update()
    {
        User user = new User(this.UserID, this.UserPW, this.UserProfile,this.RegList);
        DatabaseReference Fdatabase = FirebaseDatabase.getInstance().getReference();
        Fdatabase.child("user_data").child(UserID).setValue(user);
        return true;
    }

}
