package FB_obj;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class fb_user_profile{

    //유저는 1 프로필, 다수의 등록이 가능
    //키는 email id로 설정한다.

    public String email = null;
    public String name = null;
    public String phone = null;
    public String mid = null;

    //public String masterid = null;

    public fb_user_profile()//get_value를 위한 빈객체
    {    }

    public fb_user_profile(String Email, String Name, String Phone,String Mid)
    {
        this.email = Email;
        this.name = Name;
        this.phone = Phone;
        this.mid = Mid;
    }
    public String get_username()
    {
        return this.name;
    }

    public String get_useremail()
    {
        return this.email;
    }

    public String get_userphone()
    {
        return this.phone;
    }

    public String get_mid()
    {
        return this.mid;
    }

    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("name", name);
        result.put("phone", phone);
        result.put("mid",mid);
        return result;
    }
}
