package FB_obj;

public class fb_message {
    //마스터에게 메시지를 보내기 위한 객체

    public String mid=null;      //수신자
    public String func=null;     //air_temp, air_power,seatbelt, play_media
    public String order=null;    //update,1/0,update,???

    public fb_message()
    {}

    public fb_message(String Mid, String Func, String Order)
    {
        this.mid  = Mid;
        this.func = func;
        this.order = Order;
    }

    public String get_mid()
    {
        return this.mid;
    }

    public String get_func()
    {
        return this.func;
    }

    public String get_order()
    {
        return this.order;
    }


    public boolean send()
    {//send msg here
        return true;

    }
}
