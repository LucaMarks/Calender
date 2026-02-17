package Dates;

public class CharException extends IllegalArgumentException {

    public CharException(int len) {
        super(len > 2
                ? "exceeds the limit of characters. Expected 2, received " + len
                : "precedes the limit of characters. Expected 2, received " + len);
    }
}
