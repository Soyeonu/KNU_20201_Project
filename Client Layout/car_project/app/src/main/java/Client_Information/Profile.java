package Client_Information;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Profile {
    private String UserName;
    //private String UserEmail;
    private String PhoneNumber;

    public Profile()
    {
        //firebase database에서 datasnapshot.getValue(Profile.class)를 참조하기 위해 필수
    }

    public Profile(String Username, String Phonenumber)
    {
        this.UserName = Username;
        this.PhoneNumber = Phonenumber;
    }
}
