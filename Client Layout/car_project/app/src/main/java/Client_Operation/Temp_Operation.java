package Client_Operation;

import Client_Information.Function;

public class Temp_Operation extends C_Operation{


    public Temp_Operation(Function f){
        super(f);
    }

    public void CheckTemp(){
        this.msg = "TEMP_CHK";
    }
}
