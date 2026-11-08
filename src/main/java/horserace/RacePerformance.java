package horserace;

/**
 * The outcome of one race for one horse.
 *
 * @param winner     whether the horse won (or tied for the win)
 * @param confidence the horse's confidence at the end of the race
 * @param fallen     whether the horse fell during the race
 *
 * @author Muhammad Hussain
 * @see Statistics
 */
public record RacePerformance(boolean winner, double confidence, boolean fallen)
{
    public RacePerformance
    {
        confidence = Math.round(confidence * 100.0) / 100.0;
    }

    @Override
    public String toString()
    {
        return "Winner: " + winner + "\n"
             + "Confidence: " + confidence + "\n"
             + "Fallen: " + fallen + "\n";
    }
}
