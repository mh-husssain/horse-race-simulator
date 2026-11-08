package horserace;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StatisticsTest
{
    @Test
    void newStatisticsAreAllZero()
    {
        Statistics stats = new Statistics();
        assertEquals(0, stats.getWins());
        assertEquals(0, stats.getLosses());
        assertEquals(0.0, stats.getWinRatio());
        assertEquals(0.0, stats.getAverageConfidence());
        assertEquals(0, stats.getFallenPercentage());
    }

    @Test
    void winRatioIsWinsOverRacesEntered()
    {
        Statistics stats = new Statistics();
        stats.addPerformance(true, 0.5, false);
        stats.addPerformance(false, 0.5, false);
        stats.addPerformance(false, 0.5, false);
        assertEquals(0.33, stats.getWinRatio());
    }

    @Test
    void winRatioIsOneWithNoLossesAndZeroWithNoWins()
    {
        Statistics winner = new Statistics();
        winner.addPerformance(true, 0.5, false);
        winner.addPerformance(true, 0.5, false);
        winner.addPerformance(true, 0.5, false);
        assertEquals(1.0, winner.getWinRatio());

        Statistics loser = new Statistics();
        loser.addPerformance(false, 0.5, false);
        loser.addPerformance(false, 0.5, false);
        assertEquals(0.0, loser.getWinRatio());
    }

    @Test
    void fallenPercentageCountsPartialFalls()
    {
        Statistics stats = new Statistics();
        stats.addPerformance(false, 0.4, true);
        stats.addPerformance(false, 0.4, true);
        stats.addPerformance(true, 0.6, false);
        stats.addPerformance(false, 0.5, false);
        stats.addPerformance(false, 0.5, false);
        assertEquals(40, stats.getFallenPercentage());
    }

    @Test
    void averageConfidenceIsTheMeanOfEndOfRaceConfidence()
    {
        Statistics stats = new Statistics();
        stats.addPerformance(true, 0.9, false);
        stats.addPerformance(false, 0.6, false);
        assertEquals(0.75, stats.getAverageConfidence());
    }

    @Test
    void historyIsReadOnly()
    {
        Statistics stats = new Statistics();
        stats.addPerformance(true, 0.9, false);
        assertThrows(UnsupportedOperationException.class,
            () -> stats.getPerformances().add(new RacePerformance(true, 1, false)));
    }
}
