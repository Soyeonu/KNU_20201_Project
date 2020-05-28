package Client_Information;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Profile {
    public String UserName;
    public String UserEmail;
    public String PhoneNumber;

    public Profile()
    {
        //firebase database에서 datasnapshot.getValue(Profile.class)를 참조하기 위해 필수
    }

    public Profile(String Username, String Phonenumber,String Email)
    {
        this.UserName = Username;
        this.UserEmail = Email;
        this.PhoneNumber = Phonenumber;
    }

    public String gain_username()
    {
        return UserName;
    }
    public String gain_email()
    {
        return UserEmail;
    }
}
