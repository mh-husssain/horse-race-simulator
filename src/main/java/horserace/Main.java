package horserace;


/**
 * Test class made of methods used to test
 * the Horse and Race java classes.
 * 
 * @author Muhammad Hussain
 * @version 1.4 (23-04-2024)
 * @see Horse
 * @see Race
 */
public class Main {
    public static void main(String[] args) {
        startRaceGUI();
    }


    /**
     * Test the Horse class
     * @return void
     */
    public static void testHorse()
    {
        Horse test1 = new Horse('A',"Horse1", 0.5);
        test1.goBackToStart();
        test1.setConfidence(0.75);
        test1.setSymbol('X');
        test1.fall();

        System.out.println(test1.getConfidence());
        System.out.println(test1.getDistanceTravelled());
        System.out.println(test1.getName());
        System.out.println(test1.getSymbol());
        System.out.println(test1.hasFallen());
    }

    /**
     * Test the Race class
     * @return Race testing object
     */
    public static Race testRace()
    {
        Race race = new Race();
        Horse horse1 = new Horse('A',"Horse1", 0.8);
        Horse horse2 = new Horse('B',"Horse2", 1);
        Horse horse3 = new Horse('C',"Horse3", 0.6);

        race.addHorse(horse1);
        race.addHorse(horse2);
        race.addHorse(horse3);

        
        race.addHorse(horse1);
        race.addHorse(new Horse('D',"Horse4", 0.9));

        return race;
        ///*while (true)*/ race.startRace();
    }

    /**
     * Test the RaceGUI class
     * @return void
     */
    public static void startRaceGUI()
    {
        RaceGUI raceGUI = new RaceGUI();
        raceGUI.setVisible(true);
    }
}