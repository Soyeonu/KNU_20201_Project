package FB_obj;

public class fb_master_info {

    //마스터의 센서 정보

    public String mid= null;         //mid
    public String air_temp= null;    //float -> String
    public String seatbelt= null;    //1,0
    public String air_power= null;   //1,0
    public String play_media= null;     //??

    public fb_master_info()
    {}

    public fb_master_info(String Mid, String Air_temp, String Seatbelt, String Air_power,String Play_media)
    {
        this.mid =Mid;
        this.air_temp=Air_temp;
        this.seatbelt = Seatbelt;
        this.air_power = Air_power;
        this.play_media = Play_media;
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
}
