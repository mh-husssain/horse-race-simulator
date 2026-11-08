package horserace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Running statistics for one horse across every race it has entered.
 *
 * @author Muhammad Hussain
 * @version 2.0
 * @see Horse
 * @see RacePerformance
 */
public class Statistics
{
    private int wins;
    private int losses;
    private final List<RacePerformance> performances = new ArrayList<>();

    /**
     * Record the outcome of a race.
     * @param winner whether the horse won or tied
     * @param confidence the horse's final confidence
     * @param fallen whether the horse fell
     */
    public void addPerformance(boolean winner, double confidence, boolean fallen)
    {
        performances.add(new RacePerformance(winner, confidence, fallen));
        if (winner)
        {
            wins++;
        }
        else
        {
            losses++;
        }
    }

    public int getWins()
    {
        return wins;
    }

    public int getLosses()
    {
        return losses;
    }

    /**
     * @return wins as a fraction of races entered (0 to 1, two decimal places); 0 before any race
     */
    public double getWinRatio()
    {
        int races = wins + losses;
        return races == 0 ? 0 : round2((double) wins / races);
    }

    /**
     * @return mean end-of-race confidence (two decimal places); 0 before any race
     */
    public double getAverageConfidence()
    {
        if (performances.isEmpty())
        {
            return 0;
        }
        double total = 0;
        for (RacePerformance race : performances)
        {
            total += race.confidence();
        }
        return round2(total / performances.size());
    }

    /**
     * @return percentage of races in which the horse fell (0 to 100, whole number); 0 before any race
     */
    public long getFallenPercentage()
    {
        if (performances.isEmpty())
        {
            return 0;
        }
        long fallen = performances.stream().filter(RacePerformance::fallen).count();
        return Math.round(100.0 * fallen / performances.size());
    }

    /**
     * @return the race history, oldest first (read-only)
     */
    public List<RacePerformance> getPerformances()
    {
        return Collections.unmodifiableList(performances);
    }

    /**
     * Human-readable summary shown in the GUI.
     * @param horse the horse these statistics belong to
     * @return multi-line summary including the full race history
     */
    public String asString(Horse horse)
    {
        StringBuilder statistics = new StringBuilder()
            .append("Horse Name: ").append(horse.getName()).append('\n')
            .append("Wins: ").append(getWins()).append('\n')
            .append("Losses: ").append(getLosses()).append('\n')
            .append("Win Rate: ").append(getWinRatio()).append('\n')
            .append("Average Confidence: ").append(getAverageConfidence()).append('\n')
            .append("Fallen Ratio: ").append(getFallenPercentage()).append("%\n\n")
            .append("Performance History: \n");

        int matchCount = 1;
        for (RacePerformance race : performances)
        {
            statistics.append("Match ").append(matchCount++).append(":\n").append(race).append('\n');
        }
        return statistics.toString();
    }

    private static double round2(double value)
    {
        return Math.round(value * 100.0) / 100.0;
    }
}
