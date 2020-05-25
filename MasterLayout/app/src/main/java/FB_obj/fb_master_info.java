package FB_obj;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fb_master_info {

    //fb에 들어갈 마스터의 센서 정보

    public String mid= null;         //mid
    public String air_temp= null;    //float -> String
    public String air_power= null;   //1,0
    public String seatbelt= null;    //1,0
    public String play_media= null;     //??
    public String login_bit=null;       //1,0

    public fb_master_info()
    {}

    public fb_master_info(String Mid, float Air_temp, int Air_power, int Seatbelt,  String Play_media, int Bit)
    {
        this.mid =Mid;
        this.air_temp=Float.toString(Air_temp);
        this.seatbelt = Integer.toString(Seatbelt);
        this.air_power = Integer.toString(Air_power);
        this.play_media = Play_media;
        this.login_bit = Integer.toString(Bit);
    }

    public fb_master_info(String Mid, String Air_temp, String  Air_power, String Seatbelt,  String Play_media,String Bit)
    {
        this.mid =Mid;
        this.air_temp=Air_temp;
        this.seatbelt = Seatbelt;
        this.air_power = Air_power;
        this.play_media = Play_media;
        this.login_bit = Bit;
    }

    public String get_mid()
    {
        return this.mid;
    }

    public String get_air_temp()
    {
        return this.air_temp;
    }

    public String get_seatbelt()
    {
        return this.seatbelt;
    }

    public String get_air_power()
    {
        return this.air_power;
    }

    public String get_play_media()
    {
        return this.play_media;
    }

    public String get_bit()
    {
        return this.login_bit;
    }

    public boolean update()       //초기값을 테이블에 입력해 업뎃
    {
        fb_master_info info = new fb_master_info(this.mid, this.air_temp, this.air_power, this.seatbelt, this.play_media, this.login_bit);
        DatabaseReference Fdatabase = FirebaseDatabase.getInstance().getReference();
        Fdatabase.child("master_info").child(mid).setValue(info);
        return true;
    }
}
