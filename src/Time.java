abstract public class Time {    //nadklasa dla clocka i stopera 
    private int second = 0;
    private int minute = 0;
    private int hour = 0;
    private int day = -1;      
    private int month = -1;
    private int year = -1;
    
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }



    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Time(){ }
    public Time(int second, int minute, int hour) {
        this.second = second;
        this.minute = minute;
        this.hour = hour;
    }

    public Time(int second, int minute, int hour, int month, int year) {
        this.second = second;
        this.minute = minute;
        this.hour = hour;
        this.month = month;
        this.year = year;
    }
    abstract void addSecond();
    abstract String show();
    abstract void fromString(String time);
}

