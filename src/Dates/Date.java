package Dates;

import java.io.Serializable;

public class Date implements Serializable {

    DateCode month;
    DateCode day;
    DateCode[] year;

    public Date(DateCode month, DateCode day){
        this.month = month; this.day = day;
    }

    public Date(DateCode month, DateCode day, DateCode[] year){
        this.month = month; this.day = day; this.year = year;
    }

    @Override
    public String toString(){
        return month.toString() + "/" + day.toString();
    }
}
