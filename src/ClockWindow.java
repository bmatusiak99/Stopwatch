

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockWindow implements InterfaceWindow{

    private static ClockWindow instance = new ClockWindow();
    private JFrame frame;
    private JButton start_btn;
    private JButton stop_btn;
    private JButton stoper_btn;
    private JTextField textField;
    private JLabel timeDisplay;
    private boolean status;

    private Clock clock;
    private Thread clockThread;

    public void init() {
        frame = new JFrame("Clock Simulator");
        frame.setSize(400,200);
        frame.setLayout(new FlowLayout());

        timeDisplay = new JLabel();
        timeDisplay.setText("01.05.2011 11:03:01");
        timeDisplay.setFont(new Font("sans-serif", Font.BOLD, 30));

        textField = new JTextField();
        textField.setText("01.05.2011 12:03:01");
        textField.setColumns(11);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("sans-serif", Font.BOLD, 30));

        start_btn = new JButton("START");
        stop_btn = new JButton("STOP");
        stoper_btn = new JButton("STOPER");
        setButton(start_btn,stop_btn,stoper_btn);

        startButton();
        stopButton();
        openStoper();

        frame.add(timeDisplay);
        frame.add(textField);
        frame.add(start_btn);
        frame.add(stop_btn);
        frame.add(stoper_btn);
        frame.setVisible(true);
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setButton(JButton ... button) {
        for (JButton jButton : button) {
            jButton.setPreferredSize(new Dimension(100,40));
        }
    }

    public void startButton(){
        status = true;      // czy petla ma sie zamknac
        start_btn.addActionListener(new ActionListener() { // ustawienie akcji nasluchiwacza
            @Override
            public void actionPerformed(ActionEvent e) {       //funckcja dodana z klasy action listener e=event akcji
                clock = new Clock();        //odpala sie nowa klasa
                clock.fromString(textField.getText());  //dolny textfield zczytuje jako stringa
                textField.setEnabled(false);        //po stopie nie mozna edytowac
                clockThread = new Thread(){     //tworzy sie nowy watek
                    @Override
                    public void run(){      //startuje watek
                        while (!isInterrupted()||status){   //petla nieskonczona dopoki watek nei jest przerwany lub status jest na false to usypia na 1s
                            try {
                                sleep(1000);
                            } catch (InterruptedException ex) {     //wyjatek bledu
                                ex.printStackTrace();       //wypisze w konsoli
                            }
                            clock.addSecond();  //dodaje sekunde do zegarka
                            timeDisplay.setText(clock.show());      //aktualizuje tekst gorny
                            textField.setText(clock.show());        //aktualizuje tekst dolny
                        }
                    }
                };
                clockThread.start();    //startowanie watku
            }
        });
    }

    public void stopButton() {  //nasluchiwacz stop button
        stop_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status = false;
                clockThread.stop();     //stopuje watek
                textField.setEnabled(true); //odblokowuje textfield
            }
        });
    }

    public void openStoper(){
        stoper_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StoperWindow(); //nowa klasa stoper guzik stopera
            }
        });
    }
}
