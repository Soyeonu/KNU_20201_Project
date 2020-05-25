package Client_Information;

import java.util.ArrayList;

public class Permission {
    public String UserID;
    public ArrayList<Function> FunctionList = new ArrayList<Function>();
    //권한은 Function 형태로 사용자에게 grant 또는 revoke 된다.
    //어떤 기능을 실행할 때, 해당 사용자의 FunctionList에 해당 Function이 존재하지 않으면 권한이 없는 것으로 간주.
    public Permission()
    {}

    public Permission (String ID){
        this.UserID = ID;
    }

    public Permission(String ID, ArrayList<Function> list)
    {
           this.UserID = ID;
           this.FunctionList = list;
    }

    public void grant(Function function){
        if(!FunctionList.contains(function)) this.FunctionList.add(function);
    }

    public void revoke(Function function){
        this.FunctionList.remove(function);
    }

    public boolean CheckPermission(Function function){
        if(this.FunctionList.contains(function)) return true;
        return false;
    }

}
