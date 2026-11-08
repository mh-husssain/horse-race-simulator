package horserace;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Writes race output into a Swing text area.
 *
 * The race runs on a background thread, so every update is posted to the
 * Swing event dispatch thread rather than touching the component directly.
 * {@code invokeLater} preserves ordering, so frames are drawn in sequence.
 *
 * @author Muhammad Hussain
 * @see RaceGUI
 */
public class TextAreaOutput implements RaceOutput
{
    private final JTextArea textArea;

    /**
     * @param textArea the text area to display output in
     */
    public TextAreaOutput(JTextArea textArea)
    {
        this.textArea = textArea;
    }

    @Override
    public void print(String text)
    {
        SwingUtilities.invokeLater(() -> textArea.append(text));
    }

    @Override
    public void clear()
    {
        SwingUtilities.invokeLater(() -> textArea.setText(""));
    }
}
