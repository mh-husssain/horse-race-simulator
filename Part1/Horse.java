
/**
 * The Horse class represents the horse object which
 * will be racing in the Race class.
 * 
 * @author Muhammad Hussain
 * @version 1.2 (22-04-2024)
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distance;
    private boolean fallen;
    private double confidence;    
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     * @param horseSymbol char symbol of the horse
     * @param horseName String name of the horse
     * @param horseConfidence double confidence of the horse
     * @return new Horse()
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
    }
    
    
    
    //Other methods of class Horse

    /**
     * Set horse to fallen and decrease confidence by 0.1
     * @return void
     */
    public void fall() {
        this.fallen = true;
        this.setConfidence(Math.round((this.getConfidence() - 0.1) * 10.0) / 10.0);
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
            this.confidence = newConfidence;
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
