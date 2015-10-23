package gudthing.serializability;

/**
 * Created by Ben on 23/10/2015.
 */
public class App {
    public static void main(String[] args) {
        //String[] schedule = { "r1x", "w2x", "r2y", "w2y" }; working
        //String[] schedule = { "r2x", "r2y", "w1x", "w2y" }; working
        //String[] schedule = {"r1x", "r2z", "r1z", "r3y", "r3y", "w1x", "w3y", "r2y", "w2z", "w2y"};// working
        //String[] schedule = {"r1x", "w2x", "w1x", "r3x"}; //1->2, 2->1, 1->3, 2->3
        String[] schedule = {"sda", "123"};
        Schedule s = new Schedule(schedule);
        // see the schedule imported
        System.out.println(s.getSchedule());

        System.out.println("Attempting Result");
        System.out.println(s.precedenceGraph());
        System.out.println("Finished");

        System.out.println("Testing- what does precedence graph show");
        System.out.println(s.conflictSerializableSolution());
    }
}
