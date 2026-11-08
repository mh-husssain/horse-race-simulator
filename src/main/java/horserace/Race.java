package horserace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A race between any number of horses, each in its own lane, plus the
 * player's single bet on the outcome.
 *
 * Originally based on the module-provided starter class by McFarewell
 * (three fixed lanes, terminal output); extended by Muhammad Hussain with
 * any number of lanes, pluggable output, statistics recording and betting.
 *
 * @author McFarewell (starter code), Muhammad Hussain
 * @version 3.0
 * @see Horse
 */
public class Race
{
    /** Starting balance for the player, in pounds. */
    public static final int STARTING_BUDGET = 75_000;

    private final Random random;
    private final long tickMillis;
    private final List<Horse> horses = new ArrayList<>();
    private RaceOutput output = RaceOutput.NONE;
    private int raceLength;

    private int budget = STARTING_BUDGET;
    private Horse betHorse;
    private int betAmount;
    private boolean wonBet;

    /**
     * A race with real randomness and a 100 ms pause between frames.
     */
    public Race()
    {
        this(new Random(), 100);
    }

    /**
     * @param random source of randomness for movement and falls (seed it for repeatable races)
     * @param tickMillis pause between frames; 0 runs the race instantly
     */
    public Race(Random random, long tickMillis)
    {
        this.random = random;
        this.tickMillis = tickMillis;
    }

    /**
     * Set the length of the track
     * @param length number of steps a horse must take to finish (at least 1)
     */
    public void setRaceLength(int length)
    {
        if (length < 1)
        {
            throw new IllegalArgumentException("Race length must be at least 1.");
        }
        raceLength = length;
    }

    /**
     * Set where the race is displayed
     * @param output the display target
     */
    public void setOutputStream(RaceOutput output)
    {
        this.output = output == null ? RaceOutput.NONE : output;
    }

    /**
     * @return the horses in lane order (read-only)
     */
    public List<Horse> getAllHorses()
    {
        return Collections.unmodifiableList(horses);
    }

    /**
     * Adds a horse to the race in the next free lane
     *
     * @param theHorse the horse to be added to the race
     * @throws IllegalArgumentException if the horse is null or already in the race
     */
    public void addHorse(Horse theHorse)
    {
        if (theHorse == null)
        {
            throw new IllegalArgumentException("Horse must not be null.");
        }
        if (horses.contains(theHorse))
        {
            throw new IllegalArgumentException(theHorse.getName() + " is already in the race.");
        }
        horses.add(theHorse);
        theHorse.setLane(horses.size());
    }

    /**
     * Removes a horse from the race and renumbers the remaining lanes
     * so they stay 1..n with no gaps. Any bet on that horse is refunded.
     *
     * @param theHorse the horse to be removed
     */
    public void delHorse(Horse theHorse)
    {
        if (!horses.remove(theHorse))
        {
            return;
        }
        if (theHorse == betHorse)
        {
            budget += betAmount;
            clearBet();
        }
        for (int i = 0; i < horses.size(); i++)
        {
            horses.get(i).setLane(i + 1);
        }
    }

    // Betting

    /**
     * Place the single bet for the next race. The stake is taken from the budget now
     * and returned double if the horse wins (or ties for the win).
     *
     * @param horse a horse in this race
     * @param amount stake in pounds, between 1 and the current budget
     * @throws IllegalArgumentException for an unknown horse or invalid amount
     * @throws IllegalStateException if a bet has already been placed
     */
    public void placeBet(Horse horse, int amount)
    {
        if (betHorse != null)
        {
            throw new IllegalStateException("A bet of £" + betAmount + " on " + betHorse.getName() + " is already placed.");
        }
        if (horse == null || !horses.contains(horse))
        {
            throw new IllegalArgumentException("Choose a horse that is in the race.");
        }
        if (amount < 1 || amount > budget)
        {
            throw new IllegalArgumentException("Bet must be between £1 and your budget of £" + budget + ".");
        }
        betHorse = horse;
        betAmount = amount;
        budget -= amount;
    }

    /**
     * Pay out the bet on the race that has just finished and clear it.
     * @return the amount paid out (0 if there was no bet or it lost)
     */
    public int settleBet()
    {
        int payout = (betHorse != null && wonBet) ? betAmount * 2 : 0;
        budget += payout;
        clearBet();
        return payout;
    }

    public int getBudget()
    {
        return budget;
    }

    public Horse getBetHorse()
    {
        return betHorse;
    }

    public int getBetAmount()
    {
        return betAmount;
    }

    /**
     * @return whether the bet horse won the most recent race
     */
    public boolean getWonBet()
    {
        return wonBet;
    }

    private void clearBet()
    {
        betHorse = null;
        betAmount = 0;
    }

    // Racing

    /**
     * Start the race
     * The horses are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */
    public void startRace()
    {
        wonBet = false;

        if (horses.isEmpty())
        {
            output.print("Add at least one horse before starting a race.\n");
            return;
        }
        if (raceLength < 1)
        {
            throw new IllegalStateException("Set a race length before starting.");
        }

        //reset all the lanes (all horses not fallen and back to 0).
        for (Horse horse : horses)
        {
            horse.goBackToStart();
            horse.stand();
        }

        output.print("\n");

        while (true)
        {
            //move each horse
            for (Horse horse : horses)
            {
                moveHorse(horse);
            }

            //print the race positions
            printRace();

            if (horses.stream().anyMatch(this::hasFinished))
            {
                break;
            }

            if (horses.stream().allMatch(Horse::hasFallen))
            {
                output.print("All horses have fallen, no winner.\n");
                recordResults(List.of());
                return;
            }

            pause();
        }

        List<Horse> winners = horses.stream().filter(this::hasFinished).toList();
        for (Horse winner : winners)
        {
            // A win raises confidence once per race.
            winner.setConfidence(winner.getConfidence() + 0.1);
        }
        // Redraw with the updated confidence, then announce (printRace clears the display first).
        printRace();
        for (Horse winner : winners)
        {
            output.print("The winner is: " + winner.getName() + "\n");
        }
        wonBet = betHorse != null && winners.contains(betHorse);
        recordResults(winners);
    }

    private void recordResults(List<Horse> winners)
    {
        for (Horse horse : horses)
        {
            horse.getStats().addPerformance(winners.contains(horse), horse.getConfidence(), horse.hasFallen());
        }
    }

    private void pause()
    {
        if (tickMillis <= 0)
        {
            return;
        }
        try
        {
            Thread.sleep(tickMillis);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
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
        if (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (random.nextDouble() < theHorse.getConfidence())
            {
                theHorse.moveForward();
            }

            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
            if (random.nextDouble() < (0.1 * theHorse.getConfidence() * theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }

    /**
     * Determines if a horse has reached the finish line. Has no side effects,
     * so it can be called any number of times per frame.
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has finished, false otherwise.
     */
    private boolean hasFinished(Horse theHorse)
    {
        return theHorse.getDistanceTravelled() >= raceLength;
    }

    /***
     * Print the race into the output
     */
    private void printRace()
    {
        output.clear();

        multiplePrint('=', raceLength * 2); //top edge of track
        output.print("\n");

        for (Horse horse : horses)
        {
            printLane(horse);
            output.print("\n");
        }

        multiplePrint('=', raceLength * 2); //bottom edge of track
        output.print("\n");
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
        int spacesBefore = Math.min(theHorse.getDistanceTravelled(), raceLength);
        int spacesAfter = raceLength - spacesBefore;

        //print a | for the beginning of the lane
        output.print("|");

        //print the spaces before the horse
        multiplePrint(' ', spacesBefore);

        //if the horse has fallen then print dead
        //else print the horse's symbol
        if (theHorse.hasFallen())
        {
            output.print("X ");
        }
        else
        {
            output.print(theHorse.getSymbol() + "");
        }

        //print the spaces after the horse
        multiplePrint(' ', spacesAfter);

        //print the | for the end of the track
        output.print("|");

        output.print("    " + theHorse.getName() + ", current confidence: " + theHorse.getConfidence());
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
        output.print(String.valueOf(aChar).repeat(Math.max(0, times)));
    }
}
