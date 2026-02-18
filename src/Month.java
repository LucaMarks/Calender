public class Month {

    public String month;
    public int days;
    public int startingDay;

    public Month(String month, int days) {
        this.month = month;
        this.days = days;
    }

    public Month(String month, int days, int startingDay) {
        this.month = month;
        this.days = days;
        this.startingDay = startingDay;
    }

    @Override
    public String toString(){
        return month;
    }
}

