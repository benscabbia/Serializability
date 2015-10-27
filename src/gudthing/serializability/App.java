package gudthing.serializability;

/**
 * App class used to launch the application
 * The class provides a template and a helper method on how to use the app
 *
 * @author Benjamin Scabbia
 * @version 1.1
 */
public class App {
    public static void main(String[] args) {
        Schedule s;
        if (args[0].equals("test")) {
            String[] schedule = {"r1x", "r2z", "r1z", "r3y", "r3y", "w1x", "w3y", "r2y", "w2z", "w2y"};// working
            s = new Schedule(schedule);

            System.out.println("To use the app, simple open command-prompt in the location of the serializability.jar file and add a schedule like shown below: \n ");
            System.out.println("java -jar serializability.jar \"r1x\", \"r2z\", \"r1z\", \"r3y\", \"r3y\", \"w1x\", \"w3y\", \"r2y\", \"w2z\", \"w2y\"\n");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("The app automatically creates an instance of the class Schedule, so we now can get the full set of results.\n");
            System.out.println("THe first line that we see is our schedule since the app calls: s.getSchedule(); ");
            System.out.println(s.getSchedule() + "\n");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("The app will now generate a precedence graph by calling: s.precedenceGraph();");
            System.out.println(s.precedenceGraph());
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Finally, the app will also provide some feedback on the graph generated, by calling: s.conflictSerializableSolution()\n");
            System.out.println(s.conflictSerializableSolution());

        } else {
            s = new Schedule(args);
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("The schedule: " + s.getSchedule());
            System.out.println();
            System.out.println("Precedence Graph: ");
            System.out.println(s.precedenceGraph());
            System.out.println(s.conflictSerializableSolution());

        }
        //String[] schedule = { "r1x", "w2x", "r2y", "w2y" }; working
        //String[] schedule = { "r2x", "r2y", "w1x", "w2y" }; working
        //String[] schedule = {"r1x", "w2x", "w1x", "r3x"}; //1->2, 2->1, 1->3, 2->3
        //Schedule s = new Schedule(schedule);
    }
}
