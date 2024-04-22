public class Test {
    public static void main(String[] args) {
        testHorse();
    }

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
    
}
