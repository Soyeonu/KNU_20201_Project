package FB_obj;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class fb_user {
    public String name;
    public String age;
    public String phone;


    public fb_user()
    {    }

    public fb_user(String Name, String Age, String Phone)
    {
        this.name = Name;
        this.age = Age;
        this.phone = Phone;
    }
    public String get_username()
    {
        return this.name;
    }

    public String get_userage()
    {
        return this.age;
    }

    public String get_userphone()
    {
        return this.phone;
    }


}



