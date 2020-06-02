package Client_Information;

import java.util.ArrayList;
import java.util.Calendar;

public class User {
    private Profile UserProfile;
    private ArrayList<Registration> RegList = new ArrayList<Registration>();
    private String UserID;
    private String UserPW;

    public User(String ID, String PW, Profile prof){
        this.UserID = ID;
        this.UserPW = PW;
        this.UserProfile = prof;
    }

    public void Register(String MasterID, Calendar Expiredate){
        Registration NewReg = new Registration(MasterID, Expiredate);
        this.RegList.add(NewReg);
    }

}
