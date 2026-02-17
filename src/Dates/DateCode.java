package Dates;


public class DateCode{

    char[] character;

    public DateCode(String code) {
        if (code.length() == 2) {
            this.character = code.toCharArray();
        } else {
            throw new CharException(code.length());
        }
    }
}
