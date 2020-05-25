package FB_obj;

    public class fb_master_login {

    //로그인 bit을 통한 업데이트

    public String mid = null;
    public String login_bit=null;

    public fb_master_login()
    {}

    public fb_master_login(String Mid,String Bit)
    {
        this.mid= Mid;
        this.login_bit = Bit;
    }

    public String get_bit()
    {
        return this.login_bit;
    }
    public String get_mid()
    {
        return  this.mid;
    }

}
