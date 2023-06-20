import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoperWindow implements InterfaceWindow{

    private JFrame frame;
    private JLabel stoper_label;
    private JButton start_btn;
    private JButton stop_btn;
    private JButton restart_btn;
    private JButton split_btn;
    private JLabel m1,m2,m3,m4,m5;
    private int m = 0;
    private int first = 0;
    private int last = 0;
    private String compare;
    private String[] memory = new String[5];
    private boolean status;

    private Thread stoperThread;
    private Stoper stoper;

    StoperWindow(){
            frame = new JFrame("Stoper");
            frame.setSize(300,410);
            frame.setLayout(new FlowLayout());

            stoper_label = new JLabel();
            stoper_label.setText("00:00:00");
            stoper_label.setFont(new Font("sans-serif", Font.BOLD, 60));

            start_btn = new JButton("START");
            stop_btn = new JButton("STOP");
            split_btn = new JButton("SPLIT");
            restart_btn = new JButton("RESTART");       //dodaje guziki
                    
            setButton(start_btn,stop_btn,split_btn,restart_btn); //dodaje rozmiary guzikow
            startButton();  
            stopButton();
            restartButton();
            splitButton();      //funkcje do obslugi guzikow

            m1 = new JLabel("1. 00:00:00   00:00:00");  //dodaje pola tkestwoe
            m2 = new JLabel("2. 00:00:00   00:00:00");
            m3 = new JLabel("3. 00:00:00   00:00:00");
            m4 = new JLabel("4. 00:00:00   00:00:00");
            m5 = new JLabel("5. 00:00:00   00:00:00");

            setLabel(m1,m2,m3,m4,m5);   //ustawia fonty itp czcionki

            frame.add(stoper_label);    //dodawane rzeczy do jframe do ramki
            frame.add(start_btn);
            frame.add(stop_btn);
            frame.add(split_btn);
            frame.add(restart_btn);
            frame.add(m1);
            frame.add(m2);
            frame.add(m3);
            frame.add(m4);
            frame.add(m5);
            frame.setVisible(true);
    }

    public void setButton(JButton ... button) {
        for (JButton jButton : button) {
            jButton.setPreferredSize(new Dimension(100,40));    //rozmiar guzikow
        }
    }
    public void setLabel(JLabel ... label){
        for (JLabel jLabel : label) {
            jLabel.setFont(new Font("sans-serif", Font.BOLD, 25));  //czcionka pogrubienie rozmiar 25
        }
    }

    public void startButton(){
        status = true;
        start_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stoper = new Stoper();  //nowa klasa stoper sie tworzy

                stoper.fromString(stoper_label.getText());  //stoper pobiera dane ze stoper_label
                stoperThread = new Thread(){    //tworzy nowy watek
                    @Override
                    public void run() {     //uruchamia
                        while(!isInterrupted()||status){    //petla nieskonczona do momentu przerwania
                            try {
                                sleep(1000);    //czeka sekunde
                            } catch (InterruptedException ex) { //wylapuje blad
                                ex.printStackTrace();       //wypisuje w konsoli
                            }
                            stoper.addSecond(); //dodaje sekunde
                            stoper_label.setText(stoper.show());       //dodaje na wyswietlacz tekst (to gorne)
                        }
                    }
                };
                stoperThread.start(); //tworzy nowy watek
            }
        });
    }
    public void stopButton(){
        stop_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status = false;     //przerwanie petli watku
                stoperThread.stop();       //stopuje watek
            }
        });
    }

    public void restartButton(){
        restart_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stoper.setSecond(0);        //zeruje czas
                stoper.setMinute(0);
                stoper.setHour(0);
                stoper_label.setText(stoper.show());    //ustawia tekst na 0000000
                m1.setText("1. 00:00:00   00:00:00");
                m2.setText("2. 00:00:00   00:00:00");
                m3.setText("3. 00:00:00   00:00:00");
                m4.setText("4. 00:00:00   00:00:00");
                m5.setText("5. 00:00:00   00:00:00");
            }
        });
    }

    public void splitButton(){
        split_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (m==5){  //nic sie nie dzieje jak powyzej 5 jest split
                    return;
                }
                if(m >= 1 && m <= 4){
                    if(m == 1){
                        last = stoper.toSec(stoper_label.getText());    //last-obecny wynik zamienia na sekundy i pobiera tekst i zmienia czas na sekundy
                        compare = stoper.compare(first,last);       //porownuje wczesniejszy i obecny wynik
                        first = last;       // ustawia ze wczesniejszy wynik staje sie obecny
                    }else{
                        last = stoper.toSec(stoper_label.getText());
                        compare = stoper.compare(first,last);
                        first = last;
                    }
                    memory[m] = m+1 + ". "+stoper_label.getText()+"   "+compare;    //m-ile elementow w tablicy pamieci compare -ostatni odjac wczesniejszy
                    m++;    //do licznika dodaje 1
                }
                if(m == 0){
                    memory[0] = "1. " + stoper_label.getText() + "   00:00:00"; //memory tablica stringow gdzie sie zapisuja wyniki, pierwszy wynik jest bez porownania
                    first = stoper.toSec(stoper_label.getText());   //first - integer ktory pobiera wynik w stringu i zamienia go na sekundy
                    m = 1;  //m to licznik ile elementow juz zapisanych jest w pamieci
                }
                m1.setText(memory[0]);      //ustawia tekst po kolei z tablicy z wynikami
                m2.setText(memory[1]);
                m3.setText(memory[2]);
                m4.setText(memory[3]);
                m5.setText(memory[4]);
            }
        });
    }

 }
