import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

/**
 * A GUI class to provide a user
 * interface for the Race class
 * 
 * @author Muhammad Hussain
 * @version 1.0 (22-04-2024)
 * @see Race
 * @see NewOutputStream
 */
public class RaceGUI extends JFrame
{
    private static JTextArea textDisplay;
    private Race race;

    /**
     * Constructor for RaceGUI
     * 
     * @param Race object we will be providing a GUI for.
     * @return RaceGUI
     */
    public RaceGUI(Race newRace)
    {
        this.race = newRace;

        setTitle("Horse Race");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Frame Panels:
        JPanel displayPanel = new JPanel();
        add(displayPanel, BorderLayout.NORTH);
        JPanel startPanel = new JPanel();
        add(startPanel, BorderLayout.SOUTH);
    // End Frame Panels

    // Start Race button
        JButton startButton = new JButton("Start Race");
        startButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                textDisplay.setText("");
                new Thread(() -> {
                    race.startRace(); // Run race logic in a separate thread
                }).start();
            }
        });
        startPanel.add(startButton);
        // End startButton


    // Text Display TextArea
        textDisplay = new JTextArea(10, 50);
        textDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textDisplay);
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        // Setting our display to be the JTextArea object via output stream
        PrintStream outputStream = new PrintStream(new NewOutputStream(textDisplay));
        System.setOut(outputStream);
    }
}