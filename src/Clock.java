import java.util.List;

class Clock extends Time {
    int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};   // po ile dni maja miec miesiace

    Clock(int second, int minute, int hour, int month, int year) {      //super konstruktor korzystam z nadklasy
        super(second, minute, hour, month, year);       
    }
    Clock() {}

    @Override
    void fromString(String time) {      
        String[] a = time.split(" ");       //rozdzielenie spacja
        String[] da = a[0].split("\\.");    //odzdzielenie daty kropkami mm.dd.yyyy
        String[] ti = a[1].split(":");      //oddzielenei dwukropkami
        setDay(Integer.parseInt(da[0]));    //ze stringa zmienia na numer
        setMonth(Integer.parseInt(da[1]));
        setYear(Integer.parseInt(da[2]));
        setHour(Integer.parseInt(ti[0]));
        setMinute(Integer.parseInt(ti[1]));
        setSecond(Integer.parseInt(ti[2]));
    }

    @Override
    void addSecond() {
        setSecond(getSecond()+1);
        if(getSecond() >= 59){
            setSecond(0);
            setMinute(getMinute()+1);
            if(getMinute() >= 59){
                setMinute(0);
                setHour(getHour()+1);
                if(getHour() >= 23){
                    setHour(0);
                    setDay(getDay()+1);
                    if(getDay() >= months[getMonth()-1]){
                        setDay(1);
                        setMonth(getMonth()+1);
                        if(getMonth() >= 12){
                            setMonth(0);
                            setYear(getYear()+1);
                        }
                    }
                }
            }
        }
    }

    @Override
    String show() {
        return String.format("%02d.%02d.%d %02d:%02d:%02d", getDay(), getMonth(), getYear(),getHour(),getMinute(),getSecond());
    }
    boolean czyPrzestepny(int rok) {
        return (rok % 4 == 0 && rok % 100 != 0 || rok % 400 == 0);
    }
}
