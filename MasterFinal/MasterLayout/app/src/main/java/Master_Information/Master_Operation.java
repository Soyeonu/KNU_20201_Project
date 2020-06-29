package Master_Information;

public class Master_Operation {
    private String msg;
    private float value;

    public void setmsg(String msg){
        this.msg = msg;
    }

    public void setval(float val){
        this.value = val;
    }

    public void SendMsg(){
        //센서로 메시지 전송
    }

    public void SendMsgWithValue(){
        //센서로 메시지와 값 전달
    }

}
