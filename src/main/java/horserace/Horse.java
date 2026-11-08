package horserace;


/**
 * The Horse class represents the horse object which
 * will be racing in the Race class.
 * 
 * @author Muhammad Hussain
 * @version 1.4 (23-04-2024)
 */
public final class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distance;
    private boolean fallen;
    private double confidence;    
    private int lane;
    private Statistics stats;
    
    // Part 2 Modifications:
    /**
     * Set the lane number of the horse
     * @param laneNumber
     */
    public void setLane(int laneNumber)
    {
        this.lane = laneNumber;
    }
    /**
     * Get the lane number of the horse
     * @return int lane
     */
    public int getLane()
    {
        return this.lane;
    }

    /**
     * Get the statistics of the horse
     * @return Statistics stats
     */
    public Statistics getStats()
    {
        return this.stats;
    }

    /**
     * Horse Object to return
     * its name as string
     * @return String name
     */
    @Override
    public String toString()
    {
        return name;
    }
    //


      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse.
     * Confidence is clamped to the range 0 to 1.
     * @param horseSymbol char symbol of the horse
     * @param horseName String name of the horse
     * @param horseConfidence double confidence of the horse
     * @return new Horse()
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.setConfidence(horseConfidence);
        this.stats = new Statistics();
    }
    
    
    
    //Other methods of class Horse

    /**
     * Set horse to fallen and decrease confidence by 0.1 (never below 0)
     */
    public void fall()
    {
        this.fallen = true;
        this.setConfidence(this.getConfidence() - 0.1);
    }

    /**
     * Set horse to standing
     * @return void
     */
    public void stand()
    {
        fallen = false;
    }
    
    /**
     * Get the confidence of the horse
     * @return double condifence
     */
    public double getConfidence()
    {
        return this.confidence;
    }
    
    /**
     * Get the distance travelled by the horse
     * @return int distance
     */
    public int getDistanceTravelled()
    {
        return this.distance;
    }
    
    /**
     * Get the name of the horse
     * @return String name
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Get the symbol of the horse
     * @return char symbol
     */
    public char getSymbol()
    {
        return this.symbol;
    }
    
    /**
     * Set the distance travelled by the horse to 0
     * @return void
     */
    public void goBackToStart()
    {
        this.distance = 0;
    }
    
    /**
     * Get the fallen value of the horse
     * @return boolean fallen
     */
    public boolean hasFallen()
    {
        return this.fallen;
    }

    /**
     * Set distance travelled by 1 increment
     * @return void
     */
    public void moveForward()
    {
        this.distance ++;
    }

    /**
     * Set the confidence of the horse
     * @param newConfidence double new confidence
     * @return void
     */
    public void setConfidence(double newConfidence)
    {
        if (newConfidence > 1)
        {
            this.confidence = 1;
        }
        else if (newConfidence < 0)
        {
            this.confidence = 0;
        }
        else
        {
            this.confidence = Math.round(newConfidence * 100.0) / 100.0;
        }
    }
    
    /**
     * Set the symbol of the horse
     * @param newSymbol String new name
     * @return void
     */
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
}