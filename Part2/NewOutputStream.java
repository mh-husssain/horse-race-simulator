
import javax.swing.JTextArea;

/**
 * Own custom OutputStream class to
 * display output of Race class into
 * RaceGUI JTextArea textDisplay.
 * 
 * @author Muhammad Hussain
 * @version 1.0 (23-04-2024)
 * @see RaceGUI
 */
public class NewOutputStream{
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
     * Write a string to the textArea.
     * @param message String to write
     */
    public void print(String message)
    {
        textArea.append(message);
    }

    /**
     * Clear the text area.
     * @return void
     */
    public void clearDisplay()
    {
        textArea.setText("");
    }
}