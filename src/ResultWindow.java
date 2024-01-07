import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class ResultWindow extends JFrame {
    JLabel titleLabel;
    JLabel speedLabel;
    JLabel messageLabel;

    public ResultWindow() {
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 200);
        this.setLocationRelativeTo(null);

        titleLabel = new JLabel("Gra ukończona!");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setBounds(0, 0, this.getWidth(), 30);

        speedLabel = new JLabel("");
        speedLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        speedLabel.setHorizontalAlignment(JLabel.CENTER);
        speedLabel.setBounds(0, 50, this.getWidth(), 20);

        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setBounds(0, 80, this.getWidth(), 20);

        this.add(titleLabel);
        this.add(speedLabel);
        this.add(messageLabel);
    }

    public void setSpeedLabel(String speedText) {
        speedLabel.setText(speedText);
    }

    public void setMessageLabel(String messageText) {
        messageLabel.setText(messageText);
    }
}
