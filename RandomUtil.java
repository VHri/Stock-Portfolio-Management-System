import java.util.Random;
public class RandomUtil {
    private static Random random_object = new Random();

    public static int randomChoiceInRange(int range_low, int range_high){
        // returns a random int range_low <= x <= range_high
        return range_low + random_object.nextInt(range_high - range_low + 1);
    }
    public static double randomProbability(){
        // returns a double between 0.0 and 1.0 with equal probability
        return Math.random();
    }
}
