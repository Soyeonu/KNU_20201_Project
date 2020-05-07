package Client_Information;

import java.util.Calendar;

public class Registration {
    private String MasterID;

    //***********************************
    private Calendar ExpireDate;
    //등록 만료 기간 (이 기간이 지나면 만료)
    private boolean Turnon_flag = false;
    //등록 만료 여부
    //***********************************

    //***********************************
    private Permission UserPermission;
    //각 등록마다 사용자에게 주어지는 권한이 상이하기 떄문에 권한 객체는 등록 객체가 소유하는 개념
    //***********************************

    //***********************************
    private boolean Primary;
    //등록되는 Master에 대해 Car Owner의 권한을 갖는가
    private boolean Secondary;
    //등록되는 Master에 대해 Temporary Owner의 권한을 갖는가 (Primary 하위개념)
    //***********************************



    public Registration(String MID, Calendar expire){
        this.MasterID = MID;
        this.ExpireDate = expire;
    }

    public void UpdateExpireDate(Calendar expire){
        this.ExpireDate = expire;
    }

    public void setFlag(boolean flag){
        this.Turnon_flag = flag;
    }

    public void setPrimary(boolean val) {this.Primary = val;}
    public boolean getPrimary() { return this.Primary; }

    public void setSecondary(boolean val) { this.Secondary = val; }
    public boolean getSecondary() { return this.Secondary; }

    public void CheckExpired(){

    }
}
