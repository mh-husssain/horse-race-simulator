package horserace;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A Race object which can add up to 3 horses
 * and then initiate a race between them!
 * 
 * @author McFarewell - last edited by Muhammad Hussain
 * @version 2.4 (26-04-2024)
 * @see Horse
 * 
 */
public class Race
{
    private NewOutputStream Output;
    private int raceLength;
    private ArrayList<Horse> horses;
    private int laneCount;
    private Horse betHorse;
    private int budget;
    private double bet;
    boolean wonBet;


    // Part 2 Modifications:
    /**
     * Set the raceLength to parameter value
     * @param length
     */
    public void setRaceLength(int length)
    {
        raceLength = length;
    }

    /**
     * Set the OutputStream stream
     * to the NewOutputStream stream
     * @param stream
     */
    public void setOutputStream(NewOutputStream stream)
    {
        Output = stream;
    }

    /**
     * Set the betHorse to the horse
     * @return ArrayList<Horse>
     */
    public ArrayList<Horse> getAllHorses()
    {
        return horses;
    }
    
    /**
     * Set the horse we place a bet on
     * @param horse
     */
    public void setBetHorse(Horse horse)
    {
        betHorse = horse;
    }

    /**
     * Get the horse we place a bet on
     * @return Horse
     */
    public Horse getBetHorse()
    {
        return betHorse;
    }

    /**
     * Set the budget of the player
     * @param budget
     */
    public void setBudget(int budget)
    {
        this.budget = budget;
    }

    /**
     * Get the budget of the player
     * @return int budget
     */
    public int getBudget()
    {
        return budget;
    }

    /**
     * Set the bet amount
     * @param double amount of bet
     */
    public void setBet(double bet)
    {
        this.bet = bet;
    }

    /**
     * Get the bet amount
     * @return double bet
     */
    public double getBet()
    {
        return bet;
    }

    /**
     * Get the wonBet boolean
     * @return boolean wonBet
     */
    public boolean getWonBet()
    {
        return wonBet;
    }

    ///
    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @return Empty Race object
     */
    public Race()
    {
        // initialise instance variables
        horses = new ArrayList<Horse>();
        laneCount = 1;
        budget = 75000;
        wonBet = false;
    }
    
    /**
     * Adds a horse to the race in the next free lane
     * 
     * @param theHorse the horse to be added to the race
     */
    public void addHorse(Horse theHorse)
    {
        horses.add(theHorse);
        theHorse.setLane(laneCount);
        this.laneCount++;
    }

    /**
     * Deletes a horse from the race
     * 
     * @param theHorse the horse to be added to the race
     */
    public void delHorse(Horse theHorse)
    {
        horses.remove(theHorse);
        this.laneCount--;
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (Horse horse : horses)
        {
            horse.goBackToStart();
            horse.stand();
        }

        Output.print("\n");
                      
        while (!finished)
        {
            //move each horse
            for (Horse horse : horses)
            {
                moveHorse(horse);
            }
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            for (Horse horse : horses)
            {
                if (raceWonBy(horse))
                {
                    finished = true;
                }
            }

        // Check if all Horses have fallen
            boolean allHorsesFallen = true;
            for (Horse horse : horses)
            {
                if (!horse.hasFallen())
                {
                    allHorsesFallen = false;
                    break;
                }
            }
            if (allHorsesFallen)
            {
                Output.print("All horses have fallen, no winner.\n");
                return;
            }
        // End check
           
            //wait for 300 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }

        printRace();
        // Display the name of the winning horse(s)! MODIFICATION
        for (Horse horse : horses)
        {
            if (raceWonBy(horse))
            {
                Output.print("The winner is: "+ horse.getName()+"\n");
                horse.getStats().addPerformance(true, horse.getConfidence(), horse.hasFallen());;
            }
            else
            {
                horse.getStats().addPerformance(false, horse.getConfidence(), horse.hasFallen());
            }
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() >= raceLength)
        {
            theHorse.setConfidence(Math.round((theHorse.getConfidence() + 0.1) * 10.0) / 10.0);
            wonBet = (theHorse == betHorse);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race into the new Output Stream
     */
    private void printRace()
    {
        Output.clearDisplay();

        multiplePrint('=',raceLength*2); //top edge of track
        Output.print("\n");
        
        for (Horse horses : horses)
        {
            printLane(horses);
            Output.print("\n");
        }

        multiplePrint('=',raceLength*2); //bottom edge of track
        Output.print("\n");    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        Output.print("|");
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            Output.print("X ");
        }
        else
        {
            Output.print(theHorse.getSymbol()+"");
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        Output.print("|");

        Output.print("    "+ theHorse.getName() +", current confidence: "+ theHorse.getConfidence());
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     * @param times the number of times to print the character
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            Output.print(aChar+"");
            i = i + 1;
        }
    }
}