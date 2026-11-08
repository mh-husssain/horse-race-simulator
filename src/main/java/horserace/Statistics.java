package horserace;

import java.util.ArrayList;

/**
 * A class to store the statistics of a horse
 * 
 * @author Muhammad Hussain
 * @version 1.0 (23-04-2024)
 * @see Horse
 * @see RacePerformance
 */
public class Statistics
{
    private int wins;
    private int losses;
    private ArrayList<RacePerformance> performances;
    private double winRatio;
    private double avgConfidence;
    private String fallenRatio;



    /**
     * Constructor for Stats module of class Horse
     * @return Statistics object
     */
    public Statistics()
    {
        this.wins = 0;
        this.losses = 0;
        this.winRatio = 0;
        this.performances = new ArrayList<RacePerformance>();
        this.avgConfidence = 0;
        this.fallenRatio = "0%";
    }

    /**
     * Get the statistics of the horse
     * @return String of all stats
     */
    public String asString(Horse horse)
    {
        String statistics = "Horse Name: " + horse.getName() + "\n";
        statistics += "Wins: " + getWins() + "\n";
        statistics += "Losses: " + getLosses() + "\n";
        statistics += "Win Rate: " + getWinRatio() + "\n";
        statistics += "Average Confidence: " + avgConfidence + "\n";
        statistics += "Fallen Ratio: " + fallenRatio + "\n\n";

        statistics += "Performance History: \n";
        int matchCount = 1;
        for (RacePerformance race : performances)
        {
            statistics += "Match " + matchCount + ":\n";
            statistics += race;
            matchCount ++;
            statistics += "\n";
        }

        return statistics;
    }
    //

    /**
     * Increment the number of wins of the horse
     * @return void
     */
    public void Win(boolean winner)
    {
        if (winner)
        {
            this.wins ++;
        }
        else
        {
            this.losses ++;
        }
    }
    /**
     * Get the number of wins of the horse
     * @return int wins
     */
    public int getWins()
    {
        return this.wins;
    }
    /**
     * Get the number of losses of the horse
     * @return int losses
     */
    public int getLosses()
    {
        return this.losses;
    }

    /**
     * Set  the new win ratio of the
     * horse after calculating it
     * @return void
     */
    public void setWinRatio()
    {
        if (this.wins == 0)
        {
            this.winRatio = (double) (0 - this.losses);
        }
        else if (this.losses == 0)
        {
            this.winRatio = (double) this.wins;
        }
        
        else
        {
            this.winRatio = (double) this.wins / (this.wins + this.losses);
        }
        // Round the win ratio to 2 decimal places
        this.winRatio = Math.round(this.winRatio * 100.0) / 100.0;
    }

    /**
     * Get the win ratio of the horse
     * @return double winRatio
     */
    public double getWinRatio()
    {
        return this.winRatio;
    }

    public void setAverages()
    {
        double totalConfidence = 0;
        int fallenCount = 0;
        for (RacePerformance race : performances)
        {
            totalConfidence += race.getConfidence();
            if (race.getFallen()) fallenCount ++;
        }

        // Calculate the average confidence of the horse
        this.avgConfidence = (totalConfidence) / this.performances.size();
        this.avgConfidence = Math.round(this.avgConfidence * 100.0) / 100.0;
        this.fallenRatio = (fallenCount / this.performances.size()) * 100 + "%";
    }


    /**
     * Add a performance to the horse's stats
     * @param boolean did horse win/tie
     * @param double horses final confidence
     * @param boolean did horse fall
     */
    public void addPerformance(boolean winner, double confidence, boolean fallen)
    {
        RacePerformance newPerformance = new RacePerformance(winner, confidence, fallen);
        this.performances.add(newPerformance);

        // Set new performance metrics
        this.Win(winner);
        this.setWinRatio();
        this.setAverages();
    }
}

/**
 * A class to store the performance of
 * each individual race.
 * 
 * @author Muhammad Hussain
 * @version 1.0 (23-04-2024)
 * @see Statistics
 */
class RacePerformance
{
    private boolean winner;
    private double confidence;
    private boolean fallen;

    @Override
    public String toString()
    {
        String performance = "Winner: " + winner + "\n";
        performance += "Confidence: " + confidence + "\n";
        performance += "Fallen: " + fallen + "\n";
        return performance;
    }

    /**
     * Constructor for RacePerformance
     * @param winner
     * @param confidence
     * @param fallen
     */
    public RacePerformance(boolean winner, double confidence, boolean fallen)
    {
        this.winner = winner;
        this.confidence = Math.round(confidence * 100.0) / 100.0;
        this.fallen = fallen;
    }

    /**
     * Get the winner status of the race performance
     * @return boolean winner
     */
    public boolean getWinner()
    {
        return this.winner;
    }

    /**
     * Get the confidence of the race performance
     * @return double confidence
     */
    public double getConfidence()
    {
        return this.confidence;
    }

    /**
     * Get the fallen status of the race performance
     * @return boolean fallen
     */
    public boolean getFallen()
    {
        return this.fallen;
    }
}
