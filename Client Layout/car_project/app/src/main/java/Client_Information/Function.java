package Client_Information;

public class Function {
    //***********************************
    //DeviceNum에 해당하는 장치에 Message를 전달해 기능 명령
    //ex) AIR001 장치에 TURNON 메시지를 보내 에어컨 켜기
    //ex) AIR001 장치에 TEMDOWN 메시지를 보내 온도 내리기 (둘이 각각 별도의 Function 객체)
    //사용할 Function들은 모두 미리 선언되어 있어야 함

    //이 Function들은 각 Device마다 상이할 것이므로, Owner에 의해 권한의 형태로 부여될 경우
    //사전에 약속된 권한의 형태가 있어야 할 것이다.
    //Master Side의 Device 클래스 안에 해당 내용을 선언할 필요가 있다.
    //Owner가 권한을 부여하려면 부여할 수 있는 모든 권한의 List가 존재해야 한다. (단순 목록이므로 별도 보관만 필요)
    //***********************************
    public String Name;
    public String DeviceNum;
    public String Message;

    public Function()
    {}

    public Function(String name, String DeviceNum){
        this.Name = name;
        this.DeviceNum = DeviceNum;
    }

    public Function(String name,String devicenum, String message)
    {
        this.Name = name;
        this.DeviceNum = devicenum;
        this.Message =message;
    }

    public void set_message(String Msg){
        this.Message = Msg;
    }

    public String get_message(){
        return this.Message;
    }

    public String get_devnum(){
        return this.DeviceNum;
    }


}
