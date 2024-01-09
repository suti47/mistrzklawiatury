import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import javax.swing.*;

/**
 * Główna klasa
 */
public class Main {
    /**
     * Metoda uruchamia grę
     * @param args
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public static void main (String args []) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Window window = new Window();
    }
}
