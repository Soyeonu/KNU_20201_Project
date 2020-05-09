package Client_Operation;

import Client_Information.Function;

public class C_Operation {
    protected String msg;
    protected Function func;
    protected float value;

    public C_Operation(Function f){
        this.func = f;
    }

    public void setmsg(String msg){
        this.msg = msg;
    }

    public void setval(float val){
        this.value = val;
    }

    public void SendMsg(){
        //서버에 메시지를 보내는 기본 메소드
    }

    public void SendMsgWithValue(){
        //서버에 메시지와 값을 같이 보내는 기본 메소드
    }

}
