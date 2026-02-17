package Dates;

public class Date {

    DateCode month;
    DateCode day;
    DateCode[] year;

    public Date(DateCode month, DateCode day){
        this.month = month; this.day = day;
    }

    public Date(DateCode month, DateCode day, DateCode[] year){
        this.month = month; this.day = day; this.year = year;
    }
}
