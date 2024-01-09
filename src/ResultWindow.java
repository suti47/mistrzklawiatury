import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;

/**
 * Klasa, wyświetlająca wyniki na końcu gry szybkiego pisania
 */
public class ResultWindow extends JFrame {
    JLabel titleLabel;
    JLabel speedLabel;
    JLabel messageLabel;

    /**
     * Ustawienia okna wyników
     */
    public ResultWindow() {
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 200);
        this.setLocationRelativeTo(null);

        //Gra ukończona!
        titleLabel = new JLabel("Gra ukończona!");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        titleLabel.setForeground(Color.MAGENTA);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setBounds(0, 0, this.getWidth(), 30);

        //WPM
        speedLabel = new JLabel("");
        speedLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        speedLabel.setForeground(Color.CYAN);
        speedLabel.setHorizontalAlignment(JLabel.CENTER);
        speedLabel.setBounds(0, 50, this.getWidth(), 20);

        //Stopień zaawansowania gracza
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        messageLabel.setForeground(Color.CYAN);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setBounds(0, 80, this.getWidth(), 20);

        this.add(titleLabel);
        this.add(speedLabel);
        this.add(messageLabel);
        this.getContentPane().setBackground(Color.BLACK);
    }

    /**
     * Ustawia tekst prędkości wpisywanych słów
     * @param speedText
     */
    public void setSpeedLabel(String speedText) {
        speedLabel.setText(speedText);
    }

    /**
     * Ustawia tekst poziomu zaawansowania gracza
     * @param messageText
     */
    public void setMessageLabel(String messageText) {
        messageLabel.setText(messageText);
    }
}
