package FB_obj;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class fb_user_profile{

    //유저는 1 프로필, 다수의 등록이 가능
    //키는 id로 설정한다.

    public String id = null;
    public String pwd = null;
    public String email = null;
    public String name = null;
    public String phone = null;
    //public String mid = null;

    public fb_user_profile()//get_value를 위한 빈객체
    {    }

    public fb_user_profile(String Id, String Pwd ,String Name, String Email, String Phone)
    {
        this.id = Id;
        this.pwd = Pwd;
        this.email = Email;
        this.name = Name;
        this.phone = Phone;
        //this.mid = Mid;
    }

    public String get_id()
    {
        return this.id;
    }

    public String get_pwd()
    {
        return this.pwd;
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

    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id",id);
        result.put("pwd",pwd);
        result.put("email", email);
        result.put("name", name);
        result.put("phone", phone);
        //result.put("mid",mid);
        return result;
    }

    //user_data에 추가
    public boolean update()
    {
        fb_user_profile profile = new fb_user_profile(this.id, this.pwd, this.name, this.email, this.phone);
        DatabaseReference Fdatabase = FirebaseDatabase.getInstance().getReference();
        Fdatabase.child("user_profile").child(id).setValue(profile);
        return true;
    }

}
