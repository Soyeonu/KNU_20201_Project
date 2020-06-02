package Client_Information;

import java.util.ArrayList;

public class Permission {
    private String RegID;
    private String FunctionName;
    //권한은 Function 형태로 사용자에게 grant 또는 revoke 된다.
    //어떤 기능을 실행할 때, 해당 사용자의 RegID에 해당하는 Registration DB에 FunctionID가 존재하지 않으면 권한이 없는 것으로 간주.

    //Empty Constructor For Firebase
    public Permission()
    {}

    public Permission (String RID){
        this.RegID = RID;
    }

    public Permission(String RID, String Func) {
        this.RegID = RID;
        this.FunctionName = Func;
    }




}
