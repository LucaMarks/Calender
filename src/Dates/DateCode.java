package Dates;


import java.io.Serializable;

public class DateCode {

    char[] character;

    public DateCode(String code) {
        if (code.length() == 2) {
            this.character = code.toCharArray();
        }else if(code.length() == 1){
            String myCode = "0" + code;
            character = myCode.toCharArray();
        } else {
            throw new CharException(code.length());
        }
    }

    @Override
    public String toString(){
        return  String.copyValueOf(character);
    }
}
