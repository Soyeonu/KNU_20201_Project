package Client_Information;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Registration {
    private static String RegID;
    private static String MasterID;

    public String ExpireDate; //"2020/12/12"
    //등록 만료 기간 (이 기간이 지나면 만료)

    public boolean Turnon_flag = false;
    //등록 만료 여부

    //Empty Constructor For Firebase
    public Registration(){}

    public Registration(String RID, String MID, String EXP){
        this.RegID = RID;
        this.MasterID = MID;
        this.ExpireDate = EXP;
    }

    public void setRegID(String RID) {this.RegID = RID;}
    public void setMasterID(String MID) {this.MasterID = MID;}
    public void setExpireDate(String EXP) {this.ExpireDate = EXP;}
    public void setTurnon_flag(Boolean val) {this.Turnon_flag = val;}

    public String getRegID() {return this.RegID;}
    public String getMasterID() {return this.MasterID;}
    public String getExpireDate() {return this.ExpireDate;}
    public Boolean getTurnon_flag() {return this.Turnon_flag;}


    public Calendar String2Calendar() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        Date date = formatter.parse(this.ExpireDate);
        cal.setTime(date);
        return cal;
    }

    public String Calendar2String(Calendar cal)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String str = formatter.format(cal.getTime());
        return str;
    }

    public void CheckExpired()
    {

    }

/*
    public void setPrimary(boolean val) {this.Primary = val;}

    public boolean getPrimary() { return this.Primary; }

    public void setSecondary(boolean val) { this.Secondary = val; }
    public boolean getSecondary() { return this.Secondary; }

    public void CheckExpired(){

    }*/

}
