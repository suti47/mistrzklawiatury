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
 * Klasa z pierwszym poziomem gry, otwierająca okno główne
 */
public class Window extends JFrame implements ActionListener, KeyListener {

    /**
     * Słowa, które pojawiają się na ekranie
     */
    String word="";
    /**
     * Wpisane słowa przez użytkownika
     */
    String typedWord="";
    /**
     * Wiadomość jaka się pojawi po ukończeniu gry
     */
    String message="";
    /**
     * Okno z wynikami
     */
    ResultWindow resultWindow;
    /**
     * Okno z drugim poziomem ortograficznym
     */
    Ortography ortographyWindow;
    /**
     * Kolor czcionki ustawiony na biały
     */
    Color textColor = Color.WHITE;

    /**
     * Przechowuje informacje jaki znak wpisał użytkownik
     */
    int typed=0;
    /**
     * Ile znaków wpisał użytkownik
     */
    int count=0;

    /**
     * Start czasu
     */
    double startTime;
    /**
     * Stop czasu
     */
    double endTime;
    /**
     * Różnica czasu
     */
    double elapsedTime;
    /**
     * Czas podany w sekundach
     */
    double secondsTime;
    /**
     * Słowa zapisane w minutę ( Words per Minute)
     */
    int WPM;

    /**
     * Czy gra aktualnie chodzi
     */
    boolean runGame;
    /**
     * Czy gra się zakończyła
     */
    boolean endGame;

    /**
     * Wysokość okna gry
     */
    final int window_height;
    /**
     * Szerokość okna gry
     */
    final int window_width;

    JButton buttonStart;
    JButton buttonLevel;
    JLabel labelLogo;
    JLabel labelTitle;
    JLabel labelStartInfo;

    ImageIcon startButtonIcon;
    ImageIcon levelButtonIcon;
    ImageIcon logo;

    /**
     * Metoda rysująca okno gry na ekranie
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public Window() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //Ustawienia okna
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window_height = 768;
        window_width = 1024;
        this.setSize(window_width, window_height);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        resultWindow = new ResultWindow();

        startButtonIcon = new ImageIcon("resources\\start.png");

        //Przycisk Start/Restart
        buttonStart = new JButton(startButtonIcon);
        buttonStart.setBounds(25, 600, 300, 100);
        buttonStart.setFont(new Font("Helvetica", Font.BOLD, 30));
        buttonStart.setForeground(Color.MAGENTA);
        buttonStart.setVisible(true);
        buttonStart.addActionListener(this);
        buttonStart.setFocusable(false);
        buttonStart.setOpaque(false);
        buttonStart.setContentAreaFilled(false);
        buttonStart.setBorderPainted(false);

        levelButtonIcon = new ImageIcon("resources\\lvl.png");

        //Przycisk Zmien Poziom
        buttonLevel = new JButton(levelButtonIcon);
        buttonLevel.setBounds(680, 600, 300, 100);
        buttonLevel.setFont(new Font("Helvetica", Font.BOLD, 30));
        buttonLevel.setForeground(Color.MAGENTA);
        buttonLevel.setVisible(true);
        buttonLevel.addActionListener(this);
        buttonLevel.setFocusable(false);
        buttonLevel.setOpaque(false);
        buttonLevel.setContentAreaFilled(false);
        buttonLevel.setBorderPainted(false);

        //Tytuł na górze
        ImageIcon title = new ImageIcon("resources\\title.png");
        labelTitle = new JLabel(title);
        labelTitle.setBounds(210, 15, 600, 200);
        labelTitle.setVisible(true);
        labelTitle.setOpaque(true);
        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        labelTitle.setBackground(Color.BLACK);

        logo = new ImageIcon("resources\\pglogo.png");

        //Logo PG
        labelLogo = new JLabel(logo);
        labelLogo.setBounds(240, 400, 500, 147);
        labelLogo.setHorizontalAlignment(JLabel.CENTER);
        labelLogo.setVisible(true);

        //Napis na początku
        labelStartInfo = new JLabel("Kliknij Start/Restart, aby rozpocząć grę!");
        labelStartInfo.setBounds(100, 200, 800, 50);
        labelStartInfo.setFont(new Font("Helvetica", Font.BOLD, 40));
        labelStartInfo.setForeground(Color.CYAN);
        labelStartInfo.setHorizontalAlignment(JLabel.CENTER);
        labelStartInfo.setVisible(true);

        this.add(labelStartInfo);
        this.add(buttonStart);
        this.add(labelTitle);
        this.add(labelLogo);
        this.add(buttonLevel);
        this.getContentPane().setBackground(Color.BLACK);
        this.addKeyListener(this);
        buttonLevel.addActionListener(this);
        this.setFocusable(true);
        this.setResizable(false);
        this.setTitle("Mistrz Klawiatury");
        this.revalidate();

    }

    /**
     * Metoda paint umożliwiająca rysowanie grafik 2D
     * @param g the specified Graphics window
     */
    @Override
    public void paint(Graphics g){
        if (runGame || endGame) {
            draw(g);
        }
    }

    /**
     * Metoda draw, która wyświetla losowe słowa w oknie, oraz kolorująca czcionkę
     * @param g
     */
    public void draw(Graphics g){
        //rysowanie i odświeżanie słów
        super.paint(g);
        g.setFont(new Font("Helvetica", Font.BOLD, 35));
        g.setColor(textColor);

        //Dzieli słowa na 4 linijki
        if(runGame){
            if(word.length()>1){
                g.drawString(word.substring(0,50), g.getFont().getSize(), g.getFont().getSize()*5);
                g.drawString(word.substring(50,100), g.getFont().getSize(), g.getFont().getSize()*7);
                g.drawString(word.substring(100,150), g.getFont().getSize(), g.getFont().getSize()*9);
                g.drawString(word.substring(150,200), g.getFont().getSize(), g.getFont().getSize()*11);
            }

            //Kolorowanie poprawnie wpisanych liter na zielono
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
            drawResults(g);//Rysuje wyniki na końcu
            //Dźwiek ukończenia gry podczas wyświetlania wyników
            File file1 = new File("resources\\yey.wav");
            AudioInputStream audioStream = null;
            try {
                audioStream = AudioSystem.getAudioInputStream(file1);
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Clip clip1 = null;
            try {
                clip1 = AudioSystem.getClip();
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            try {
                clip1.open(audioStream);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            clip1.start();
        }
    }

    /**
     * Metoda wyświetlająca okno z wynikami na końcu gry
     * @param g
     */
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
        endGame=false;
    }

    /**
     * Metoda, która umożliwia wykonanie akcji po wciśnięciu buttonów, w moim wypadku Start/Restart oraz zmianę poziomu
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Start/Restart Gry
        if(e.getSource()==buttonStart){
                word = "";
                typedWord = "";
                message = "";
                textColor = Color.WHITE;

                word = getWords();
                typed = 0;
                count = 0;
                runGame = true;
                endGame = false;
                resultWindow.setVisible(false);
                labelStartInfo.setVisible(false);
                repaint();
        }
        //Zmiana poziomu
        else if (e.getSource() == buttonLevel) {
            if (ortographyWindow == null) {
                ortographyWindow = new Ortography();
            }
            ortographyWindow.setVisible(true);
            this.dispose();
            word = "";
            typedWord = "";
            message = "";
            textColor = Color.WHITE;

            word = getWords();
            typed = 0;
            count = 0;
            runGame = true;
            endGame = false;
            resultWindow.setVisible(false);
            repaint();
        }
    }

    /**
     * Metoda, która głównie powoduje zmiany po wciśnięciu klawisza (gra dźwięk, liczy słowa, liczy WPM)
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        this.repaint();
        //Dźwięk Klikania na maszynie do pisania
        File file = new File("resources\\click.wav");
        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
        try {
            clip.open(audioStream);
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        clip.start();
        //Liczenie Words Per Minute (WPM)
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
            //Liczenie wpisywanych słów
            char [] words=word.toCharArray();
            if(typed<200){
                runGame=true;
                if(e.getKeyChar()==words[typed]){
                    typedWord=typedWord+words[typed];
                    typed++;
                    count++;
                }
                else {
                    textColor = Color.RED; //Błędnie wpisywane litery, koloruje na czerwono
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Metoda, która powoduje zmiany po wyciśnięciu przycisku
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        textColor = Color.WHITE; //Poprawna wpisana litera zmienia tekst na biało
    }

    /**
     * Metoda, która bierze losowe słowa, które pojawią się w grze na ekranie
     * @return
     */
    public static String getWords(){

        //Lista słów
        ArrayList<String> Words = new ArrayList<String>();
        String words1 = "ból numer orkiestra wróbel film biurko autobus próba gondola atrament proszek plaża podkowa uchodźca wyspa gęś tramwaj baranina pantera granit scena kurier kapusta głowa wakacje kruszyna bank wróżka rewanż kult";
        String words2 = "świeca dworzec autobus szubienica prawnik zjeżdżalnia wróżka korona święty muzyka piknik kod ostryga koło zamachowe egipt hobby t-shirt parafia wąż licencja zatoka faks judo tortury ławka dżet glina artyleria emigrant guzik dziennikarz";
        String words3 = "kometa gitara internet ogień ochraniacz chatka karaluch absolwent golf masaż przynęta podskok butik plakat żarówka opera brzuch czołg dym koncert flirt sanie piosenka azyl prześcieradło jacht plaster fala tłuszcz";
        String words4 = "zderzenie kostka paw ankieta reforma ramię aspiryna słoń zapalniczka trampolina motocykl ślimak obraza ogon polityk foka flaga szkoła warzywo teatr telefon lider papieros system mata chrapanie wida kuchenka elektryczny fotel bujać";
        String words5 = "strych grad mydło fryzjer łza sport stacja karta kredytowa kolonia krzesło szafka odrzut szpilka pożyczka pogoda teatr ikona nowicjusz dłoń zmrok zadanie łobuz trąbka druty podróż orzechy słoń wtyczka święty wodorosty lekarstwo struś paranoja klucz perkusja tłuszcz";


        Words.add(words1);
        Words.add(words2);
        Words.add(words3);
        Words.add(words4);
        Words.add(words5);

        //Randomowe wybieranie słów, które się pojawią na ekranie
        Random random = new Random();
        int position = (random.nextInt(5));

        String toReturn = Words.get(position).substring(0, 200);
        if(toReturn.charAt(199)==32){ //Jeżeli ostatni znak jest pustym polem
            toReturn=toReturn.strip(); //Usuwa puste pole
            if (toReturn.charAt(0) == 32) {
                toReturn = toReturn.substring(1); // Pominięcie początkowej spacji
            }
            toReturn=toReturn+"."; //Kropka lub spacja za ostatnik słowem
        }
        return(toReturn);
    }
}