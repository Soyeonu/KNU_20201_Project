package FB_obj;

public class fb_message {

    //메시지 수신용
    //마스터는 메시지를 쓰지 않고 master_info 테이블을 업데이트 한다.
    //클라측에서 리스너를 작성하면 이는 자동적으로 업뎃

    public String func =null;       //사용자가 업뎃을 원하는 기능 -> fb_master_info에서 air_onoff, play_media
    public String order=null;       //1,0 / 미디어를 플레이하는 링크나... 다른거?

    public fb_message()
    {}

    public fb_message(String Func, String Order)
    {
        this.func = Func;
        this.order = Order;
    }

    public String get_func()
    {
        return this.func;
    }

    public String get_order()
    {
        return this.order;
    }

}
