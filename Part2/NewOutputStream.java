
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 * Own custom OutputStream class to
 * display output of Race class into
 * RaceGUI JTextArea textDisplay.
 * 
 * @author Muhammad Hussain
 * @version 1.0 (22-04-2024)
 * @see RaceGUI
 */
public class NewOutputStream extends OutputStream {
    private JTextArea textArea;

    /**
     * Constructor for NewOutputStream
     * @param textArea JTextArea to display output
     */
    public NewOutputStream(JTextArea textArea)
    {
        this.textArea = textArea;
    }

    /**
     * Write a byte to the textArea.
     * Output Stream must have this method.
     * 
     * @param b byte to write
     */
    @Override
    public void write(int b)
    {
        textArea.append(String.valueOf((char) b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Write a string to the textArea.
     * @param message String to write
     */
    public void write(String message)
    {
        textArea.append(message);
    }
}