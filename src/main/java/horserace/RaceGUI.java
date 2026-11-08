package horserace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A GUI class to provide a user
 * interface for the Race class
 * 
 * @author Muhammad Hussain
 * @version 2.0
 * @see Race
 * @see TextAreaOutput
 */
public class RaceGUI extends JFrame
{
    private static final String NAME_PLACEHOLDER = "Enter Horse Name....";

    private JTextArea textDisplay;
    private TextAreaOutput outputStream;
    private final Race race;
    private JSpinner raceTracks;
    private JSpinner raceDistance;
    private JSpinner horseConfidence;
    private JComboBox<Horse> horseSelector;
    private JTextArea horseStats;
    private JSpinner betSpinner;
    private JTextArea bettingStats;
    private JButton[] controls;

    /**
     * Constructor for RaceGUI
     * 
     * @param Race object we will be providing a GUI for.
     * @return RaceGUI
     */
    public RaceGUI()
    {
        this.race = new Race();

        setTitle("Horse Race");
        setSize(1100, 760);
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
        JPanel bettingPanel = new JPanel();

    // Border Layouts/Titles
    displayPanel.setBorder(BorderFactory.createTitledBorder("Display Panel:"));


    controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel:"));
    customisePanel.setLayout(new BoxLayout(customisePanel, BoxLayout.Y_AXIS));
    racePanel.setBorder(BorderFactory.createTitledBorder("Customise Race!"));
    racePanel.setLayout(new BoxLayout(racePanel, BoxLayout.Y_AXIS));
    horsePanel.setBorder(BorderFactory.createTitledBorder("Customise Horse!"));
    horsePanel.setLayout(new BoxLayout(horsePanel, BoxLayout.Y_AXIS));

    statsPanel.setBorder(BorderFactory.createTitledBorder("Select a Horse:"));
    statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

    bettingPanel.setBorder(BorderFactory.createTitledBorder("Place a Bet:"));
    bettingPanel.setLayout(new BoxLayout(bettingPanel, BoxLayout.Y_AXIS));

    // Set panel backgrounds
    controlPanel.setBackground(Color.DARK_GRAY);
    controlPanel.setForeground(Color.white);
    displayPanel.setBackground(Color.gray);
    startPanel.setBackground(Color.DARK_GRAY);
    racePanel.setBackground(Color.lightGray);
    horsePanel.setBackground(Color.lightGray);
    statsPanel.setBackground(Color.lightGray);
    
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
        textDisplay = new JTextArea(12, 50);
        textDisplay.setEditable(false);
        textDisplay.setFont(new Font("monospace", Font.PLAIN, 18));
        textDisplay.setBackground(Color.black);
        textDisplay.setForeground(Color.white);

        JScrollPane scrollPane = new JScrollPane(textDisplay);
        displayPanel.add(scrollPane, BorderLayout.WEST);
        textDisplay.setText("Welcome to the Horse Race game!\n");
        outputStream = new TextAreaOutput(textDisplay);
        this.race.setOutputStream(outputStream);

    // Start Race button
        JButton startButton = new JButton("Start Race");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButton();
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
    JTextField horseName = new JTextField(NAME_PLACEHOLDER);
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
            String name = horseName.getText().trim();
            if (name.isEmpty() || name.equals(NAME_PLACEHOLDER))
            {
                showError("Enter a name for the horse first.");
                return;
            }
            char symbol = (char) horseSymbol.getSelectedItem();
            double confidence = (double) horseConfidence.getValue();
            confidence = Math.round(confidence * 100.0) / 100.0;
            // Create a new horse object
            Horse newHorse = new Horse(symbol, name, confidence);
            race.addHorse(newHorse);
            raceTracks.setValue((int) raceTracks.getValue() + 1);
            outputStream.print("New horse added: " + newHorse.getName() + ", Symbol: " + newHorse.getSymbol() + ", Confidence: " + newHorse.getConfidence() + ", Lane: " + newHorse.getLane()+"\n");
            horseSelector.addItem(newHorse);
        }
    });
    horsePanel.add(addHorseButton);
//

// View Horse Statistics
    horseSelector = new JComboBox<>();
    horseStats = new JTextArea(11, 20);
    horseStats.setEditable(false);
    horseStats.setFont(new Font("monospace", Font.PLAIN, 14));
    JScrollPane horseStatsScrollPane = new JScrollPane(horseStats);
    horseSelector.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            refreshSelectedHorse();
        }
    });
    // Delete Horse Button
    JButton deleteHorseButton = new JButton("Delete Horse");
    deleteHorseButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            Horse selectedHorse = (Horse) horseSelector.getSelectedItem();
            if (selectedHorse == null)
            {
                showError("Select a horse to delete.");
                return;
            }
            race.delHorse(selectedHorse);
            textDisplay.append("\n"+selectedHorse.getName() + " has been removed from the race!\n");
            horseSelector.removeItem(selectedHorse);
            raceTracks.setValue((int) raceTracks.getValue() - 1);
        }
    });
    statsPanel.add(horseSelector);
    statsPanel.add(horseStatsScrollPane);
    horsePanel.add(deleteHorseButton);
    controlPanel.add(statsPanel, BorderLayout.NORTH);
    horseStats.setBackground(Color.black);
    horseStats.setForeground(Color.white);
//
// Enter Betting System
    JLabel betLabel = new JLabel("Enter your bet:");
    SpinnerNumberModel bettingSpinner = new SpinnerNumberModel(50, 0, 10000, 50);
    betSpinner = new JSpinner(bettingSpinner);
    betSpinner.setToolTipText("Enter your bet amount");
    betSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
    betSpinner.setAlignmentY(Component.TOP_ALIGNMENT);
    JButton placeBetButton = new JButton("Place Bet");
    placeBetButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            placeBetButton();
        }
    });
    placeBetButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    placeBetButton.setAlignmentY(Component.TOP_ALIGNMENT);
    bettingStats = new JTextArea(11, 20);
    bettingStats.setEditable(false);
    bettingStats.setFont(new Font("monospace", Font.PLAIN, 14));
    bettingStats.append("Current Budget: £" + race.getBudget());
    JScrollPane bettingScrollPane = new JScrollPane(bettingStats);
    bettingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


    bettingPanel.add(betLabel);
    bettingPanel.add(betSpinner);
    bettingPanel.add(placeBetButton);
    bettingPanel.add(bettingScrollPane);
    controlPanel.add(bettingPanel, BorderLayout.EAST);
    bettingPanel.setBackground(Color.lightGray);

    // Initialise button layouts
    initialiseButtons(startButton, addHorseButton, deleteHorseButton, placeBetButton);
    controls = new JButton[] {startButton, addHorseButton, deleteHorseButton, placeBetButton};
    }

    /**
     * Show the selected horse's statistics and the betting summary.
     */
    private void refreshSelectedHorse()
    {
        Horse selectedHorse = (Horse) horseSelector.getSelectedItem();
        horseStats.setText(selectedHorse == null ? "" : selectedHorse.getStats().asString(selectedHorse));
        clearBettingDisplay(selectedHorse);
    }

    /**
     * Reset the betting display to the budget, any open bet and the selected horse
     * @param selectedHorse the horse to describe, or null
     */
    public void clearBettingDisplay(Horse selectedHorse)
    {
        bettingStats.setText("Current Budget: £" + race.getBudget() + "\n");
        if (race.getBetHorse() != null)
        {
            bettingStats.append("Bet placed: £" + race.getBetAmount() + " on " + race.getBetHorse().getName() + "\n");
        }
        if (selectedHorse != null)
        {
            bettingStats.append("\nHorse Name: " + selectedHorse.getName() + "\nSymbol: " + selectedHorse.getSymbol() + "\n");
        }
    }

    /**
     * Process when the Start Race button is clicked. The race runs on a
     * background thread so the window stays responsive; the controls are
     * disabled until it finishes so the race state cannot change mid-race.
     */
    public void startButton()
    {
        race.setRaceLength((int) raceDistance.getValue());
        textDisplay.setText("");
        setControlsEnabled(false);

        Thread raceThread = new Thread(() -> {
            race.startRace();

            SwingUtilities.invokeLater(() -> {
                boolean hadBet = race.getBetHorse() != null;
                String betHorseName = hadBet ? race.getBetHorse().getName() : null;
                int payout = race.settleBet();
                refreshSelectedHorse();
                if (hadBet)
                {
                    bettingStats.append(payout > 0
                        ? "\nCongratulations, " + betHorseName + " won!\nYou take home £" + payout + ".\n"
                        : "\nSorry, " + betHorseName + " did not win. You lost the bet.\n");
                }
                setControlsEnabled(true);
            });
        }, "race");
        raceThread.setDaemon(true);
        raceThread.start();
    }

    /**
     * Process when a bet is placed on the selected horse.
     */
    public void placeBetButton()
    {
        Horse selectedHorse = (Horse) horseSelector.getSelectedItem();
        int amount = (int) betSpinner.getValue();
        try
        {
            race.placeBet(selectedHorse, amount);
        }
        catch (IllegalArgumentException | IllegalStateException e)
        {
            showError(e.getMessage());
            return;
        }
        clearBettingDisplay(selectedHorse);
        bettingStats.append("\nYou have placed a bet of £" + amount + " on " + selectedHorse.getName() + "!\n");
    }

    private void setControlsEnabled(boolean enabled)
    {
        for (JButton button : controls)
        {
            button.setEnabled(enabled);
        }
    }

    private void showError(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Horse Race", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Initialise the buttons
     * @return void
     */
    public void initialiseButtons(JButton startButton, JButton addHorseButton, JButton deleteHorseButton, JButton placeBetButton)
    {
        // Restyle the startButton
        startButton.setBackground(Color.LIGHT_GRAY);
        startButton.setBorderPainted(false);
        startButton.setOpaque(true);
        startButton.setPreferredSize(new Dimension(150, 40)); 

        // Restyle the addHorseButton
        addHorseButton.setBackground(Color.LIGHT_GRAY);
        addHorseButton.setBorderPainted(false);
        addHorseButton.setOpaque(true);
        addHorseButton.setPreferredSize(new Dimension(150, 40)); 

        // Restyle the deleteHorseButton
        deleteHorseButton.setBackground(Color.LIGHT_GRAY);
        deleteHorseButton.setBorderPainted(false);
        deleteHorseButton.setOpaque(true);
        deleteHorseButton.setPreferredSize(new Dimension(150, 40)); 

        // Restyle the placeBetButton
        placeBetButton.setBackground(Color.LIGHT_GRAY);
        placeBetButton.setBorderPainted(false);
        placeBetButton.setOpaque(true);
        placeBetButton.setPreferredSize(new Dimension(150, 40));
    }
}