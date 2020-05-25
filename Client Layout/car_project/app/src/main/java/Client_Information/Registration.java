package Client_Information;

import java.util.Calendar;

public class Registration {
    public String MasterID;

    //***********************************
    public String ExpireDate; //"2020/12/12"
    //등록 만료 기간 (이 기간이 지나면 만료)
    public boolean Turnon_flag = false;
    //등록 만료 여부
    //***********************************

    //***********************************
    public Permission UserPermission;
    //각 등록마다 사용자에게 주어지는 권한이 상이하기 떄문에 권한 객체는 등록 객체가 소유하는 개념
    //***********************************

    //***********************************
    public boolean Primary;
    //등록되는 Master에 대해 Car Owner의 권한을 갖는가
     public boolean Secondary;
    //등록되는 Master에 대해 Temporary Owner의 권한을 갖는가 (Primary 하위개념)
    //***********************************

    public Registration()
    {}

    public Registration(String MID, String expire){
        this.MasterID = MID;
        this.ExpireDate = expire;
    }

    public Registration(String MID, String expire,Permission perm) {
        this.MasterID = MID;
        this.ExpireDate = expire;
        this.UserPermission = perm;
    }

    public Registration(String MID, String expire, boolean flag, Permission perm, boolean primary, boolean secondary)
    {
        this.MasterID = MID;
        this.ExpireDate = expire;
        this.Turnon_flag = flag;
        this.UserPermission = perm;
        this.Primary = primary;
        this.Secondary = secondary;
    }

    public void UpdateExpireDate(String expire){
        this.ExpireDate = expire;
    }

    public void set_flag(boolean flag){
        this.Turnon_flag = flag;
    }

    public void set_primary(boolean val)
    {
        this.Primary = val;
    }

    public void set_secondary(boolean val)
    {
        this.Secondary = val;
    }

    public boolean get_primary() { return this.Primary; }

    public void CheckExpired(){

    }

/*
    public void setPrimary(boolean val) {this.Primary = val;}

    public boolean getPrimary() { return this.Primary; }

    public void setSecondary(boolean val) { this.Secondary = val; }
    public boolean getSecondary() { return this.Secondary; }

    public void CheckExpired(){

    }*/

}
