package FB_obj;

import android.provider.MediaStore;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class fb_user_permission {
    //권한의 경우 0:있음, 1:없음
    // 현재 총 3개의 권한으로 나눔
    // 하위키가 랜덤, 피식별자, 식별자를 적어야함.

    public int airconAuth;
    public int usersettingAuth;
    public int mediaAuth;
    public int etcAuth;
    public String mid;
    public String email;
    public String spot = null;
    public String exdate = null;  //datetime으로 변환해서 사용해야 함.

    public fb_user_permission()//get_value를 위한 빈객체
    {    }

    public fb_user_permission(int AirconAuth, int UsersettingAuth, int MediaAuth, int EtcAuth,
                              String Mid, String Email,String Spot, String Exdate)
    {
        this.airconAuth = AirconAuth;
        this.usersettingAuth = UsersettingAuth;
        this.mediaAuth = MediaAuth;
        this.etcAuth = EtcAuth;
        this.mid = Mid;
        this.email = Email;
        this.spot = Spot;
        this.exdate = Exdate;
    }

    public int get_airconAuth()
    {
        return this.airconAuth;
    }
    public int get_usersettinAuth()
    {
        return this.usersettingAuth;
    }
    public int get_mediaAuth()
    {
        return this.mediaAuth;
    }
    public int get_etcAuth()
    {
        return this.etcAuth;
    }
    public String get_mid()
    {
        return this.mid;
    }
    public String get_spot()
    {
        return  this.spot;
    }
    public String get_email()
    {
        return this.email;
    }
    public String get_exdate()
    {
        return this.exdate;
    }

    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("airconAuth",airconAuth);
        result.put("usersettingAuth", usersettingAuth);
        result.put("mediaAuth",mediaAuth);
        result.put("etcAuth", etcAuth);
        result.put("mid",mid);
        result.put("email",email);

        return result;
    }

}
