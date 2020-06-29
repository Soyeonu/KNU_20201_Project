package MasterInfo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Masterinfo {

    //fb에 들어갈 마스터의 센서 정보

    public String masterid;         //마스터 id
    public String air_temp;    //float -> String
    public String air_power;   //1,0
    public String seatbelt;    //1,0
    public String play_media;     //??
    public String login_bit;       //1,0

    public Masterinfo()
    {}

    public Masterinfo(String Masterid, float Air_temp, int Air_power, int Seatbelt,  String Play_media, int Bit)
    {
        this.masterid =Masterid;
        this.air_temp=Float.toString(Air_temp);
        this.seatbelt = Integer.toString(Seatbelt);
        this.air_power = Integer.toString(Air_power);
        this.play_media = Play_media;
        this.login_bit = Integer.toString(Bit);
    }

    public Masterinfo(String Masterid, String Air_temp, String  Air_power, String Seatbelt,  String Play_media,String Bit)
    {
        this.masterid = Masterid;
        this.air_temp=Air_temp;
        this.seatbelt = Seatbelt;
        this.air_power = Air_power;
        this.play_media = Play_media;
        this.login_bit = Bit;
    }

    public String gain_masterid()
    {
        return this.masterid;
    }

    public String gain_air_temp()
    {
        return this.air_temp;
    }

    public String gain_seatbelt()
    {
        return this.seatbelt;
    }

    public String gain_air_power()
    {
        return this.air_power;
    }

    public String gain_play_media()
    {
        return this.play_media;
    }

    public String gain_bit()
    {
        return this.login_bit;
    }

    public boolean update()       //초기값을 테이블에 입력해 업뎃
    {
        Masterinfo info = new Masterinfo(this.masterid, this.air_temp, this.air_power, this.seatbelt, this.play_media, this.login_bit);
        DatabaseReference Fdatabase = FirebaseDatabase.getInstance().getReference();
        Fdatabase.child("master_info").child(masterid).setValue(info);
        return true;
    }
}
