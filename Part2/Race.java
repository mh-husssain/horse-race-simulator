import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A Race object which can add up to 3 horses
 * and then initiate a race between them!
 * 
 * @author McFarewell - last edited by Muhammad Hussain
 * @version 2.1 (22-04-2024)
 * @see Horse
 * 
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;
    private int laneCount;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
        laneCount = 1;
    }
    
    /**
     * Adds a horse to the race in the next free lane
     * 
     * @param theHorse the horse to be added to the race
     */
    public void addHorse(Horse theHorse)
    {
        if (lane1Horse == theHorse || lane2Horse == theHorse || lane3Horse == theHorse)
        {
            System.out.println("This horse has already been added to the race!");
        }
        else if (laneCount > 3)
        {
            System.out.println("There are only a maximum of 3 lanes! Cannot add more horses.");
        }
        else // Add the horse to the lane
        {
            if (laneCount == 1)
            {
                lane1Horse = theHorse;
            }
            else if (laneCount == 2)
            {
                lane2Horse = theHorse;
            }
            else if (laneCount == 3)
            {
                lane3Horse = theHorse;
            }
            else
            {
                System.out.println("Cannot add horse to lane " + laneCount + " because there is no such lane");
            }
            laneCount++;
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        // Racing horses array
        Horse[] horses = {lane1Horse, lane2Horse, lane3Horse};
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (Horse horse : horses)
        {
            horse.goBackToStart();
            horse.stand();
        }

        System.out.println("\n");
                      
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

            if (lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen())
            {
                printRace();
                System.out.println("All horses have fallen, no winner");
                return;
            }
           
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
                System.out.println("The winner is: "+ horse.getName());
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
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            theHorse.setConfidence(Math.round((theHorse.getConfidence() + 0.1) * 10.0) / 10.0);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print("\033[H\033[2J");  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        printLane(lane1Horse);
        System.out.println();
        
        printLane(lane2Horse);
        System.out.println();
        
        printLane(lane3Horse);
        System.out.println();
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
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
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('X');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');

        System.out.print("    "+ theHorse.getName() +", current confidence: "+ theHorse.getConfidence());
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
            System.out.print(aChar);
            i = i + 1;
        }
    }
}