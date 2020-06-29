package Master_Information;

import java.util.ArrayList;

public class Master {
    private String Name;
    private ArrayList<String> UserIDList = new ArrayList<String>();
    //유저 아이디를 담은 배열
    private ArrayList<Master_Operation> OperationList = new ArrayList<Master_Operation>();
    //해당 master의 사용 가능한 operation 들을 담은 배열

    public Master(String Name){
        this.Name = Name;
    }

    public void LoadUserIDList(){
        //최초 구동시 반드시 실행
        //서버로부터 ID 리스트 받아옴
    }
    public void SaveUserIDList(){
        //종료시 반드시 실행
        //서버에 UserList 저장
    }

    public void LoadOperationList(){
        //최초 구동시 반드시 실행
        //서버로부터 Operation 리스트 받아옴
    }

    public void SaveOperationList(){
        //종료시 반드시 실행
        //서버에 OperationList 저장
    }

    public void setName(String name) {this.Name = name;}
    public String getName() {return this.Name;}

    public void Register(String UserID){
        //새 사용자가 등록될 때 ID 추가
        this.UserIDList.add(UserID);
    }

    public void NewOperation(Master_Operation oper){
        //새 Operation 추가 시 리스트에 등록
        this.OperationList.add(oper);
    }
}
