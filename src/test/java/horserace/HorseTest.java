package horserace;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HorseTest
{
    @Test
    void constructorClampsConfidenceToZeroToOne()
    {
        assertEquals(1.0, new Horse('A', "High", 1.5).getConfidence());
        assertEquals(0.0, new Horse('B', "Low", -0.2).getConfidence());
    }

    @Test
    void setConfidenceRoundsToTwoDecimalPlaces()
    {
        Horse horse = new Horse('A', "Ace", 0.5);
        horse.setConfidence(0.456);
        assertEquals(0.46, horse.getConfidence());
    }

    @Test
    void fallMarksHorseFallenAndLowersConfidenceByExactlyPointOne()
    {
        Horse horse = new Horse('A', "Ace", 0.55);
        horse.fall();
        assertTrue(horse.hasFallen());
        assertEquals(0.45, horse.getConfidence());
    }

    @Test
    void fallNeverTakesConfidenceBelowZero()
    {
        Horse horse = new Horse('A', "Ace", 0.05);
        horse.fall();
        assertEquals(0.0, horse.getConfidence());
    }

    @Test
    void standAndGoBackToStartResetTheHorse()
    {
        Horse horse = new Horse('A', "Ace", 0.5);
        horse.moveForward();
        horse.moveForward();
        horse.fall();

        horse.stand();
        horse.goBackToStart();

        assertFalse(horse.hasFallen());
        assertEquals(0, horse.getDistanceTravelled());
    }
}
