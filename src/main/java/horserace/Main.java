package horserace;

import javax.swing.SwingUtilities;

/**
 * Entry point: opens the race window on the Swing event dispatch thread.
 *
 * @author Muhammad Hussain
 */
public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new RaceGUI().setVisible(true));
    }
}
