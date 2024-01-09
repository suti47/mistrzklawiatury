import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;

/**
 * Klasa wyświetlająca poziom ortograficzny
 */
public class Ortography extends JFrame implements ActionListener {

    /**
     * Słowa do uzupełnienia
     */
    String[] questions = 	{
            "_AK", //HAK
            "P__CZOŁA", //PSZCZOŁA
            "_UK", //ŻUK
            "_SEMKA", //ÓSEMKA
            "M_AWKA", //MŻAWKA
            "BO_ATER", //BOHATER
            "O_YDNY", //OHYDNY
            "PR_SZYĆ", //PRÓSZYĆ
            "SK_WKA", //SKUWKA
            "ŹR_DŁO" //ŹRÓDŁO
    };

    /**
     * Możliwości do wybrania
     */
    String[][] options = 	{
            {"H","CH"},
            {"RZ","SZ"},
            {"Ż","RZ"},
            {"U","Ó"},
            {"Ż", "RZ"},
            {"CH", "H"},
            {"H", "CH"},
            {"U", "Ó"},
            {"Ó", "U"},
            {"Ó", "U"}
    };

    /**
     * Prawidłowe odpowiedzi
     */
    char[] answers = 		{
            'A',
            'B',
            'A',
            'B',
            'A',
            'B',
            'A',
            'B',
            'B',
            'A'

    };

    /**
     * Odpowiedź
     */
    char answer;
    /**
     * Zmienna przechowująca ilość słów wyświetlonych
     */
    int index;
    /**
     * Prawidłowe odpowiedzi
     */
    int correct_guesses =0;
    /**
     * Łączna ilość słów w grze
     */
    int total_questions = questions.length;
    /**
     * Wynik
     */
    int result;
    /**
     * Ilośc sekund na odpowiedź
     */
    int seconds=10;

    JTextArea textarea = new JTextArea();
    ImageIcon chlvl = new ImageIcon("resources\\lvl.png");
    JButton changelvl = new JButton(chlvl);
    JLabel answer_labelA = new JLabel();
    JLabel answer_labelB = new JLabel();
    JLabel time_label = new JLabel();
    JLabel seconds_left = new JLabel();
    JTextField number_right = new JTextField();
    JTextField percentage = new JTextField();
    ImageIcon a = new ImageIcon("resources\\A.png");
    ImageIcon b = new ImageIcon("resources\\B.png");
    JButton buttonA = new JButton(a);
    JButton buttonB = new JButton(b);
    ImageIcon slowka = new ImageIcon("resources\\słówka.png");
    JLabel textfield = new JLabel(slowka);

     //Czas
    Timer timer = new Timer(1000, new ActionListener() {
         /**
          * Liczenie czasu od 10 sekund w dół
          * @param e the event to be processed
          */
        @Override
        public void actionPerformed(ActionEvent e) {
           seconds--;
           seconds_left.setText(String.valueOf(seconds));
           if(seconds<=0){
               displayAnswer();
           }
        }
    });

    /**
     * Ustawienie okna gry ortograficznej
     */
    public Ortography (){
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750, 450);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.BLACK);
        this.setResizable(false);

        //Logo u góry
        textfield.setBounds(0,0, 750, 50);
        textfield.setBackground(Color.BLACK);
        textfield.setForeground(Color.MAGENTA);
        textfield.setFont(new Font("Helvetica", Font.BOLD, 40));
        textfield.setBorder(BorderFactory.createBevelBorder(1));
        textfield.setHorizontalAlignment(JTextField.CENTER);

        //Słowa
        textarea.setBounds(300,50, 750, 50);
        textarea.setBackground(Color.BLACK);
        textarea.setForeground(Color.MAGENTA);
        textarea.setFont(new Font("Helvetica", Font.BOLD, 30));
        textarea.setBorder(BorderFactory.createBevelBorder(1));
        textfield.setHorizontalAlignment(JTextField.CENTER);
        textarea.setEditable(false);

        //Przycisk A
        buttonA.setBounds(0,100,100,100);
        buttonA.setFont(new Font("MV Boli",Font.BOLD,35));
        buttonA.setFocusable(false);
        buttonA.addActionListener(this);
        buttonA.setOpaque(false);
        buttonA.setContentAreaFilled(false);
        buttonA.setBorderPainted(false);

        //Przycisk B
        buttonB.setBounds(0,200,100,100);
        buttonB.setFont(new Font("MV Boli",Font.BOLD,35));
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);
        buttonB.setOpaque(false);
        buttonB.setContentAreaFilled(false);
        buttonB.setBorderPainted(false);

        //Przycisk zmiany poziomu
        changelvl.setBounds(225,300,300,100);
        changelvl.setFont(new Font("MV Boli",Font.BOLD,35));
        changelvl.setFocusable(false);
        changelvl.addActionListener(this);
        changelvl.setOpaque(false);
        changelvl.setContentAreaFilled(false);
        changelvl.setBorderPainted(false);

        //Odpowiedź A
        answer_labelA.setBounds(125,100,500,100);
        answer_labelA.setBackground(Color.BLACK);
        answer_labelA.setForeground(Color.CYAN);
        answer_labelA.setFont(new Font("Artegra",Font.PLAIN,35));
        answer_labelA.setText("B");

        //Odpowiedź B
        answer_labelB.setBounds(125,200,500,100);
        answer_labelB.setBackground(Color.BLACK);
        answer_labelB.setForeground(Color.CYAN);
        answer_labelB.setFont(new Font("Artegra",Font.PLAIN,35));
        answer_labelB.setText("B");

        //Ile sekund zostało
        seconds_left.setBounds(625,200,100,100);
        seconds_left.setBackground(Color.BLACK);
        seconds_left.setForeground(Color.MAGENTA);
        seconds_left.setFont(new Font("Ink Free",Font.BOLD,60));
        seconds_left.setBorder(BorderFactory.createBevelBorder(1));
        seconds_left.setOpaque(true);
        seconds_left.setHorizontalAlignment(JTextField.CENTER);
        seconds_left.setText(String.valueOf(seconds));

        //Wyświetlony czas
        time_label.setBounds(625,300,100,25);
        time_label.setBackground(Color.BLACK);
        time_label.setForeground(Color.CYAN);
        time_label.setFont(new Font("MV Boli",Font.PLAIN,16));
        time_label.setHorizontalAlignment(JTextField.CENTER);
        time_label.setText("Czas!");

        //Ile odpowiedzi dobrze
        number_right.setBounds(325,100,200,100);
        number_right.setBackground(Color.BLACK);
        number_right.setForeground(Color.CYAN);
        number_right.setFont(new Font("Ink Free",Font.BOLD,50));
        number_right.setBorder(BorderFactory.createBevelBorder(1));
        number_right.setHorizontalAlignment(JTextField.CENTER);
        number_right.setEditable(false);

        //Ile odpowiedzi dobrze w %
        percentage.setBounds(325,200,200,100);
        percentage.setBackground(Color.BLACK);
        percentage.setForeground(Color.CYAN);
        percentage.setFont(new Font("Ink Free",Font.BOLD,50));
        percentage.setBorder(BorderFactory.createBevelBorder(1));
        percentage.setHorizontalAlignment(JTextField.CENTER);
        percentage.setEditable(false);

        this.add(time_label);
        this.add(changelvl);
        this.add(number_right);
        this.add(percentage);
        this.add(seconds_left);
        this.add(answer_labelA);
        this.add(answer_labelB);
        this.add(buttonA);
        this.add(buttonB);
        this.add(textarea);
        this.add(textfield);
        this.setTitle("Słówka");

        nextQuestion();
    }

    /**
     * Metoda robiąca działania, gdy zostanie wcisnięty przycisk Zmiany poziomu lub odpowiedzi
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);

        //Zmiana poziomu
        if (e.getSource() == changelvl) {
            Window window = null;
            try {
                window = new Window();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            window.setVisible(true);
            window.repaint();
            window.revalidate();
        }
        if(e.getSource()==buttonA){
            answer = 'A';
            if(answer == answers[index]){
                correct_guesses++;
            }
        }
        if(e.getSource()==buttonB){
            answer = 'B';
            if(answer == answers[index]){
                correct_guesses++;
            }
        }
        displayAnswer();
    }

    /**
     * Przejście do następnego pytania, ustawianie odpowiedzi
     */
    public void nextQuestion(){
        if(index >= total_questions){
            results();
        }
        else {
            textarea.setText(questions[index]);
            answer_labelA.setText(options[index][0]);
            answer_labelB.setText(options[index][1]);
            timer.start();
        }
    }

    /**
     * Pokazuje poprawną i błędną odpowiedź
     */
    public void displayAnswer(){
        timer.stop();
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);

        if(answers[index] != 'A'){
            answer_labelA.setForeground(Color.RED);
        }
        if(answers[index] != 'B'){
            answer_labelB.setForeground(Color.RED);
        }
        if(answers[index] == 'A'){
            answer_labelA.setForeground(Color.GREEN);
        }
        if(answers[index] == 'B'){
            answer_labelB.setForeground(Color.GREEN);
        }

        //Czas po której zmienia się pytanie po udzielonej odpowiedzi
        Timer pause = new Timer(2000, new ActionListener() {
            /**
             * Czas po którym zmienia się pytanie po udzielonej odpowiedzi
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                answer_labelA.setForeground(Color.CYAN);
                answer_labelB.setForeground(Color.CYAN);

                answer = ' ';
                seconds=10;
                seconds_left.setText(String.valueOf(seconds));
                buttonA.setEnabled(true);
                buttonB.setEnabled(true);
                index++;
                nextQuestion();
            }
        });
        pause.setRepeats(false);
        pause.start();
    }

    /**
     * Wyniki wyświetlane na koniec gry
     */
    public void results(){
        //Dźwięk grany przy przedstawieniu wyników
        File file2 = new File("resources\\yey.wav");
        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(file2);
        } catch (UnsupportedAudioFileException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Clip clip2 = null;
        try {
            clip2 = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
        try {
            clip2.open(audioStream);
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        clip2.start();
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);

        result = (int)((correct_guesses/(double)total_questions)*100);

        textarea.setText("");
        answer_labelA.setText("");
        answer_labelB.setText("");

        number_right.setText(correct_guesses+"/"+total_questions);
        percentage.setText(result+"%");

        this.add(number_right);
        this.add(percentage);
    }
}
