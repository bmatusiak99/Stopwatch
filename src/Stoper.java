public class Stoper extends Time{

    public Stoper(int second, int minute, int hour) {
        super(second, minute, hour);
    }

    public Stoper() {
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
                if(getHour()>=999){
                    setHour(0);
                    setMinute(0);
                    setSecond(0);
                }
            }
        }
    }

    @Override
    String show() {
        return String.format("%02d:%02d:%02d", getHour(),getMinute(),getSecond()); //formater czyli zmienia liczby z 1 na 01
    }

    @Override
    void fromString(String time) {
        String[] result = time.split(":");  //splitowanie rozdziela po prostu string tablica  12 15 10
        setSecond(Integer.parseInt(result[2])); //zmienia string na integer i ustawia sekundy czyli 2 element czyli sekundy
        setMinute(Integer.parseInt(result[1])); //1 elementy - minuty
        setHour(Integer.parseInt(result[0]));   //0 element -godziny
    }

    int toSec (String time){    //zmienia na sekundy
        String[] result = time.split(":");
        return Integer.parseInt(result[2])+Integer.parseInt(result[1])*60+Integer.parseInt(result[0])*3600;
    }
    String  compare (int first, int last){  //porownuje
        int result = last-first;    //w sekundach czas
        int hours = result/3600;    //dziele przez godzine
        int minutes = (result%3600)/60; //reszta z dzielenia przez godzine i potem dziele przez 60
        int seconds = ((result%3600)%60);   //reszta z dzielenie przez godziny i minuty i zostaja sekundy
        return String.format("%02d:%02d:%02d",hours,minutes,seconds);   //formater
    }
}
