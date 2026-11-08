package horserace;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RaceTest
{
    private FixedRandom random;
    private CapturingOutput output;
    private Race race;

    // With random fixed at 0.5: fast (0.8) and quick (0.7) move every frame, slow (0.3) never moves; none fall.
    private Horse fast;
    private Horse quick;
    private Horse slow;

    @BeforeEach
    void setUp()
    {
        random = new FixedRandom(0.5);
        output = new CapturingOutput();
        race = new Race(random, 0);
        race.setOutputStream(output);
        race.setRaceLength(5);

        fast = new Horse('A', "Fast", 0.8);
        quick = new Horse('B', "Quick", 0.7);
        slow = new Horse('C', "Slow", 0.3);
    }

    @Test
    void fastestHorseWinsAndGainsConfidenceOnce()
    {
        race.addHorse(fast);
        race.addHorse(slow);

        race.startRace();

        assertEquals(5, fast.getDistanceTravelled());
        assertEquals(0.9, fast.getConfidence(), "a win adds 0.1 confidence exactly once");
        assertEquals(0.3, slow.getConfidence());
        assertTrue(output.currentFrame().contains("The winner is: Fast"));
    }

    @Test
    void resultsAreRecordedInEachHorsesStatistics()
    {
        race.addHorse(fast);
        race.addHorse(slow);

        race.startRace();

        assertEquals(1, fast.getStats().getWins());
        assertEquals(1, slow.getStats().getLosses());
    }

    @Test
    void tiedHorsesBothWinAndABetOnEitherPaysOut()
    {
        race.addHorse(fast);
        race.addHorse(quick);
        race.placeBet(quick, 100);

        race.startRace();

        assertEquals(1, fast.getStats().getWins());
        assertEquals(1, quick.getStats().getWins());
        assertTrue(race.getWonBet());
        assertEquals(200, race.settleBet());
        assertEquals(Race.STARTING_BUDGET + 100, race.getBudget());
    }

    @Test
    void losingBetKeepsTheStake()
    {
        race.addHorse(fast);
        race.addHorse(slow);
        race.placeBet(slow, 500);

        race.startRace();

        assertEquals(0, race.settleBet());
        assertEquals(Race.STARTING_BUDGET - 500, race.getBudget());
    }

    @Test
    void raceWhereEveryHorseFallsIsRecordedAsALossForAll()
    {
        random.value = 0.0;
        race.addHorse(fast);
        race.addHorse(slow);

        race.startRace();

        assertTrue(output.currentFrame().contains("All horses have fallen"));
        assertEquals(1, fast.getStats().getLosses());
        assertEquals(100, fast.getStats().getFallenPercentage());
    }

    @Test
    void previousWinDoesNotPayOutAgainWhenEveryHorseFalls()
    {
        race.addHorse(fast);
        race.addHorse(slow);
        race.placeBet(fast, 100);
        race.startRace();
        race.settleBet();

        random.value = 0.0;
        race.placeBet(fast, 100);
        race.startRace();

        assertFalse(race.getWonBet());
        assertEquals(0, race.settleBet());
    }

    @Test
    void removingAHorseRenumbersTheRemainingLanes()
    {
        race.addHorse(fast);
        race.addHorse(quick);
        race.addHorse(slow);

        race.delHorse(quick);

        assertEquals(1, fast.getLane());
        assertEquals(2, slow.getLane());
        assertEquals(2, race.getAllHorses().size());
    }

    @Test
    void removingTheBetHorseRefundsTheStake()
    {
        race.addHorse(fast);
        race.placeBet(fast, 250);

        race.delHorse(fast);

        assertNull(race.getBetHorse());
        assertEquals(Race.STARTING_BUDGET, race.getBudget());
    }

    @Test
    void theSameHorseCannotBeAddedTwice()
    {
        race.addHorse(fast);
        assertThrows(IllegalArgumentException.class, () -> race.addHorse(fast));
    }

    @Test
    void betsAreValidated()
    {
        race.addHorse(fast);

        assertThrows(IllegalArgumentException.class, () -> race.placeBet(slow, 100), "horse not in race");
        assertThrows(IllegalArgumentException.class, () -> race.placeBet(fast, 0), "zero stake");
        assertThrows(IllegalArgumentException.class, () -> race.placeBet(fast, Race.STARTING_BUDGET + 1), "more than budget");

        race.placeBet(fast, 100);
        assertThrows(IllegalStateException.class, () -> race.placeBet(fast, 100), "second bet");
    }

    @Test
    void startingWithNoHorsesExplainsInsteadOfFailing()
    {
        race.startRace();
        assertTrue(output.currentFrame().contains("Add at least one horse"));
    }

    @Test
    void raceLengthMustBePositive()
    {
        assertThrows(IllegalArgumentException.class, () -> race.setRaceLength(0));
    }
}
