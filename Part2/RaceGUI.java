import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * A GUI class to provide a user
 * interface for the Race class
 * 
 * @author Muhammad Hussain
 * @version 1.2 (23-04-2024)
 * @see Race
 * @see NewOutputStream
 */
public class RaceGUI extends JFrame
{
    private static JTextArea textDisplay;
    private Race race;
    private JSpinner raceTracks;
    private JSpinner raceDistance;
    private JTextField horseName;
    private JComboBox<Character> horseSymbol;
    private JSpinner horseConfidence;
    private ArrayList<Horse> horses;
    private JComboBox<Horse> horseSelector;

    /**
     * Constructor for RaceGUI
     * 
     * @param Race object we will be providing a GUI for.
     * @return RaceGUI
     */
    public RaceGUI()
    {
        // Initialising fields
        horses = new ArrayList<Horse>();
        this.race = new Race(); // Initialised in JTextArea block

        setTitle("Horse Race");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Frame Panels:
    JPanel controlPanel = new JPanel();
    JPanel displayPanel = new JPanel();
    JPanel startPanel = new JPanel();
    // Child Panels
        JPanel customisePanel = new JPanel();
            JPanel racePanel = new JPanel();
            JPanel horsePanel = new JPanel();
        JPanel statsPanel = new JPanel();

    // Border Layouts/Titles
    controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel:"));
    customisePanel.setLayout(new BoxLayout(customisePanel, BoxLayout.Y_AXIS));
    racePanel.setBorder(BorderFactory.createTitledBorder("Customise Race!"));
    racePanel.setLayout(new BoxLayout(racePanel, BoxLayout.Y_AXIS));
    horsePanel.setBorder(BorderFactory.createTitledBorder("Customise Horse!"));
    horsePanel.setLayout(new BoxLayout(horsePanel, BoxLayout.Y_AXIS));

    statsPanel.setBorder(BorderFactory.createTitledBorder("View Horse Statistics:"));
    statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
    
    // Add panels to the frame
    add(controlPanel, BorderLayout.NORTH);
    controlPanel.add(customisePanel, BorderLayout.WEST);
        customisePanel.add(racePanel, BorderLayout.NORTH);
        customisePanel.add(horsePanel, BorderLayout.SOUTH);

    add(displayPanel, BorderLayout.CENTER);
    add(startPanel, BorderLayout.SOUTH);

    // Insets padding = new Insets(0, 10, 0, 0);
    // displayPanel.setBorder(BorderFactory.createEmptyBorder(35, 35, 0, 0));
// End Frame
    // Text Display TextArea
        textDisplay = new JTextArea(15, 50);
        textDisplay.setEditable(false);
        textDisplay.setFont(new Font("monospace", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(textDisplay);
        displayPanel.add(scrollPane, BorderLayout.WEST);
        textDisplay.setText("Welcome to the Horse Race game!\n");
        NewOutputStream outputStream = new NewOutputStream(textDisplay);
        this.race.setOutputStream(outputStream);

    // Start Race button
        JButton startButton = new JButton("Start Race");
        startButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                horses = race.getAllHorses();
                int distance = (int) raceDistance.getValue();
                race.setRaceLength(distance);
                textDisplay.setText("");
                new Thread(() -> {
                    race.startRace(); // Run race logic in a separate thread
                }).start();
            }
        });
        startPanel.add(startButton);
    // End startButton

// Race Tracks ComboBox
    JLabel raceTracksLabel = new JLabel("Number of Tracks:");
    SpinnerNumberModel raceTracksSpinner = new SpinnerNumberModel(0, 0, 10, 1);
    raceTracks = new JSpinner(raceTracksSpinner);
    JSpinner.NumberEditor raceTracksEditor = new JSpinner.NumberEditor(raceTracks, "#");
    raceTracks.setEditor(raceTracksEditor);
    raceTracks.setEnabled(false);
    racePanel.add(raceTracksLabel);
    racePanel.add(raceTracks);
// 

// Race Distance Spinner
    JLabel raceDistanceLabel = new JLabel("Track Length:");
    SpinnerNumberModel raceDistanceSpinner = new SpinnerNumberModel(5, 5, 20, 1);
    raceDistance = new JSpinner(raceDistanceSpinner);
    JSpinner.NumberEditor raceDistanceEditor = new JSpinner.NumberEditor(raceDistance, "#");
    raceDistance.setEditor(raceDistanceEditor);
    racePanel.add(raceDistanceLabel);
    racePanel.add(raceDistance);
//

// Horse Name TextField
    JTextField horseName = new JTextField("Enter Horse Name....");
    horsePanel.add(horseName);
//

// Horse Symbol ComboBox
    JLabel horseSymbolLabel = new JLabel("Horse Symbol:");
    JComboBox<Character> horseSymbol = new JComboBox<Character>(new Character[] {'♘', '♔', '♞', '♖', '♗', '♕', '♙', '♚', '♛', '♜', '♝', '♞', '♟'});
    horsePanel.add(horseSymbolLabel);
    horsePanel.add(horseSymbol);
//

// Horse Confidence Spinner
    JLabel horseConfidenceLabel = new JLabel("Horse Confidence:");
    SpinnerNumberModel horseConfidenceSpinner = new SpinnerNumberModel(0.5, 0, 1, 0.1);
    horseConfidence = new JSpinner(horseConfidenceSpinner);
    JSpinner.NumberEditor horseConfidenceEditor = new JSpinner.NumberEditor(horseConfidence, "0.0#");
    horseConfidence.setEditor(horseConfidenceEditor);
    horsePanel.add(horseConfidenceLabel);
    horsePanel.add(horseConfidence);
//

// Add Horse Button
    JButton addHorseButton = new JButton("Add Horse");
    addHorseButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            // Get the values from the input fields
            String name = horseName.getText();
            char symbol = (char) horseSymbol.getSelectedItem();
            double confidence = (double) horseConfidence.getValue();
            confidence = Math.round(confidence * 100.0) / 100.0;
            // Create a new horse object
            Horse newHorse = new Horse(symbol, name, confidence);
            race.addHorse(newHorse);
            raceTracks.setValue((int) raceTracks.getValue() + 1);
            outputStream.print("New horse added: " + newHorse.getName() + ", Symbol: " + newHorse.getSymbol() + ", Confidence: " + newHorse.getConfidence() + ", Lane: " + newHorse.getLane()+"\n");
            horses = race.getAllHorses();
            horseSelector.addItem(newHorse);
        }
    });
    horsePanel.add(addHorseButton);
//

// View Horse Statistics
    horseSelector = new JComboBox<>();
    JTextArea horseStats = new JTextArea(11, 20);
    horseStats.setEditable(false);
    horseStats.setFont(new Font("monospace", Font.PLAIN, 14));
    JScrollPane horseStatsScrollPane = new JScrollPane(horseStats);
    horseSelector.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            Horse selectedHorse = (Horse) horseSelector.getSelectedItem();
            horseStats.setText("");
            horseStats.append(selectedHorse.getStats().asString(selectedHorse));
        }
    });

    statsPanel.add(horseSelector);
    statsPanel.add(horseStatsScrollPane);
    controlPanel.add(statsPanel, BorderLayout.EAST);
//
    }

    /**
     * Clear the text display
     * @return void
     */
    public static void clearDisplay()
    {
        textDisplay.setText("");
    }
}