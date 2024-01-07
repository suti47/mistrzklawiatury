import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class Window extends JFrame implements ActionListener, KeyListener {

    String word="";
    String typedWord="";
    String message="";

    ResultWindow resultWindow;

    Color textColor = Color.WHITE;

    ArrayList<Boolean> correctLetters = new ArrayList<>();
    ArrayList<Color> letterColors = new ArrayList<>();

    int typed=0;
    int count=0;

    double startTime;
    double endTime;
    double elapsedTime;
    double secondsTime;
    int WPM;

    boolean runGame;
    boolean endGame;
    boolean startGame;

    final int window_height;
    final int window_width;
    final int delay = 100;

    Timer timer;
    JButton buttonStart;
    JButton buttonLevel;
    JLabel labelLogo;
    JLabel labelLevel;
    JLabel labelTitle;
    JWindow resultPanel;

    ImageIcon startButtonIcon;
    ImageIcon levelButtonIcon;
    ImageIcon logo;
    ImageIcon backgroundImage;
    ImageIcon title;



    public Window(){
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window_height = 768;
        window_width = 1024;
        this.setSize(window_width, window_height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        resultWindow = new ResultWindow();

        startButtonIcon = new ImageIcon("resources\\start.png");

        buttonStart = new JButton(startButtonIcon);
        buttonStart.setBounds(25, 600, 300, 100);
        buttonStart.setFont(new Font("Helvetica", Font.BOLD, 30));
        buttonStart.setForeground(Color.MAGENTA);
        buttonStart.setVisible(true);
        buttonStart.addActionListener(this);
        buttonStart.setFocusable(false);

        levelButtonIcon = new ImageIcon("resources\\lvl.png");

        buttonLevel = new JButton(levelButtonIcon);
        buttonLevel.setBounds(680, 600, 300, 100);
        buttonLevel.setFont(new Font("Helvetica", Font.BOLD, 30));
        buttonLevel.setForeground(Color.MAGENTA);
        buttonLevel.setVisible(true);
        buttonLevel.addActionListener(this);
        buttonLevel.setFocusable(false);

        ImageIcon title = new ImageIcon("resources\\title.png");
        labelTitle = new JLabel(title);
        labelTitle.setBounds(210, 15, 600, 200);
        labelTitle.setVisible(true);
        labelTitle.setOpaque(true);
        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        labelTitle.setBackground(Color.BLACK);

        logo = new ImageIcon("resources\\pglogo.png");

        labelLogo = new JLabel(logo);
        labelLogo.setBounds(240, 400, 500, 147);
        labelLogo.setHorizontalAlignment(JLabel.CENTER);
        labelLogo.setVisible(true);


        this.add(buttonStart);
        this.add(labelTitle);
        this.add(labelLogo);
        this.add(buttonLevel);
        this.getContentPane().setBackground(Color.BLACK);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setResizable(false);
        this.setTitle("Mistrz Klawiatury");
        this.revalidate();

    }

    @Override
    public void paint(Graphics g){
        //super.paint(g);
        if (runGame || endGame) {
            draw(g);
        }
    }

    public void draw(Graphics g){
        //super.paint(g);
        g.setFont(new Font("Helvetica", Font.BOLD, 35));
        g.setColor(textColor);

        if(runGame){
            if(word.length()>1){
                g.drawString(word.substring(0,50), g.getFont().getSize(), g.getFont().getSize()*5);
                g.drawString(word.substring(50,100), g.getFont().getSize(), g.getFont().getSize()*7);
                g.drawString(word.substring(100,150), g.getFont().getSize(), g.getFont().getSize()*9);
                g.drawString(word.substring(150,200), g.getFont().getSize(), g.getFont().getSize()*11);
            }

            g.setColor(Color.GREEN);
            if(typedWord.length()>0){
                if(typed<50)
                    g.drawString(typedWord.substring(0, typed), g.getFont().getSize(), g.getFont().getSize()*5);
                else
                    g.drawString(typedWord.substring(0, 50), g.getFont().getSize(), g.getFont().getSize()*5);
            }
            if(typedWord.length()>50){
                if(typed<100)
                    g.drawString(typedWord.substring(50, typed), g.getFont().getSize(), g.getFont().getSize()*7);
                else
                    g.drawString(typedWord.substring(50, 100), g.getFont().getSize(), g.getFont().getSize()*7);
            }
            if(typedWord.length()>100){
                if(typed<150)
                    g.drawString(typedWord.substring(100, typed), g.getFont().getSize(), g.getFont().getSize()*9);
                else
                    g.drawString(typedWord.substring(100, 150), g.getFont().getSize(), g.getFont().getSize()*9);
            }
            if(typedWord.length()>150){
                g.drawString(typedWord.substring(150, typed), g.getFont().getSize(), g.getFont().getSize()*11);
                runGame=false;
            }
            if (typedWord.length() > typed) {
                g.setColor(Color.RED);  // Ustaw kolor na czerwony, jeśli naciśnięto zły klawisz
                g.drawString(typedWord.substring(typed), g.getFont().getSize(), g.getFont().getSize() * 5);
            }
        }
        if(endGame)
        {
            drawResults(g);
        }
    }

    public void drawResults(Graphics g){

        if(WPM<=40)
            message="Jesteś średni, ale może być lepiej!";
        else if(WPM>40 && WPM<=60)
            message="Jesteś naprawdę dobry!";
        else if(WPM>60 && WPM<=100)
            message="Jesteś wybitny!";
        else
            message="Jesteś elitarnie dobry!";

        resultWindow.setTitle("Gra ukończona!");
        resultWindow.setSpeedLabel("Prędkość pisania: " + WPM + " Słów na Minutę (WPM)");
        resultWindow.setMessageLabel(message);

        resultWindow.setVisible(true);
        //timer.stop();
        endGame=false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==buttonStart){
                word = "";
                typedWord = "";
                message = "";
                textColor = Color.WHITE;

                word = getWords();
                typed = 0;
                count = 0;
                //timer = new Timer(500, this);
                //timer.start();
                runGame = true;
                endGame = false;
                resultWindow.setVisible(false);
                repaint();
        }
//        if(runGame){
//            repaint();
//        }
//        if(endGame){
//            repaint();
//        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.repaint();
        if(word.length()>1){
            if(count==0)
                startTime=LocalTime.now().toNanoOfDay();
            else if (count==200){
                endTime=LocalTime.now().toNanoOfDay();
                elapsedTime=endTime-startTime;
                secondsTime=elapsedTime/1000000000.0;
                WPM = (int) (((200.0/5)/secondsTime)*60);
                endGame=true;
                runGame=false;
                count++;
            }
            char [] words=word.toCharArray();
            if(typed<200){
                runGame=true;
                if(e.getKeyChar()==words[typed]){
                    typedWord=typedWord+words[typed];
                    typed++;
                    count++;
                }
                else {
                    textColor = Color.RED;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        textColor = Color.WHITE;
    }

    public static String getWords(){

        ArrayList<String> Words = new ArrayList<String>();
        String words1 = "ból numer orkiestra wróbel film biurko autobus próba gondola atrament proszek plaża podkowa uchodźca wyspa gęś tramwaj baranina pantera granit scena kurier kapusta głowa wakacje kruszyna bank wróżka rewanż kult";
        String words2 = "świeca dworzec autobus szubienica prawnik zjeżdżalnia wróżka korona święty muzyka piknik kod ostryga koło zamachowe egipt hobby t-shirt parafia wąż licencja zatoka faks judo tortury ławka dżet glina artyleria emigrant guzik dziennikarz";

        Words.add(words1);
        Words.add(words2);

        Random random = new Random();
        int position = (random.nextInt(2));

        String toReturn = Words.get(position).substring(0, 200);
        if(toReturn.charAt(199)==32){
            toReturn=toReturn.strip();
            if (toReturn.charAt(0) == 32) {
                toReturn = toReturn.substring(1); // Pominięcie początkowej spacji
            }
            toReturn=toReturn+".";
        }
        return(toReturn);
    }
}