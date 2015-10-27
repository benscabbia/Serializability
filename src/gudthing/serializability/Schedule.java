package gudthing.serializability;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Schedule class is the main class for the application.
 * The class provides a number of methods which can populate a schedule, test for conflict serializability
 * draw a precedence graph.
 *
 * @author Benjamin Scabbia
 * @version 1.1
 */
public class Schedule {
    // the schedule which holds the operations
    LinkedList<Operation> theSchedule;

    /**
     * Default constructor for objects of class Schedule.
     *
     * @param schedule is a string array containing the schedule
     */
    Schedule(String[] schedule) {
        try {
            theSchedule = new LinkedList<Operation>();
            populate(schedule);
        } catch (Exception e) {
            System.out.println("There was an error with the importing of the schedule");
            System.out.println("The exception: ");
            e.printStackTrace();
        }
    }

    /**
     * Method is used by the constructor to parse the array and invoke append() method
     *
     * @param schedule to be tested
     */
    private void populate(String[] schedule) {
        for (int i = 0; i < schedule.length; i++) {
            String action = schedule[i].substring(0, 1);
            int transaction = Integer.parseInt(schedule[i].substring(1, 2));
            String item = schedule[i].substring(2);
            append(action, transaction, item);
        }
        System.out.println(theSchedule.size());
    }

    /**
     * Method to append an item of type Operation to the LinkedList
     *
     * @param action      The action of the transaction (read or write)
     * @param transaction The number of the transaction
     * @param item        The item which is affected
     */
    private void append(String action, int transaction, String item) {
        theSchedule.add(new Operation(action, transaction, item));
    }

    /**
     * Method which returns an item from the linkedlist theSchedule by the given index
     *
     * @param index of the item in the list
     * @return an object of type operation
     */
    public Operation getItem(int index) {
        return theSchedule.get(index);
    }

    /**
     * Method which returns a string representation of the full schedule
     *
     * @return a string containing the full schedule
     */
    public String getSchedule() {
        String schedule = "";
        for (Operation s : theSchedule) {
            schedule += s + ", ";
        }
        return schedule;
    }

    /**
     * Method which evaluates a given schedule and explains whether it's conflict/not conflict serializable
     * and where a cycle is found, iff is found.
     *
     * @return a string explaining the result of the schedule
     */
    public String conflictSerializableSolution() {
        String result = "Is Schedule Conflict-Serializable: ";
        HashSet<OperationConflict> conflicts = conflictSerializableTest();
        for (OperationConflict i : conflicts) {
            Operation outerFrom = i.getFromOperation();
            Operation outerTo = i.getToOperation();
            for (OperationConflict j : conflicts) {
                Operation innerFrom = j.getFromOperation();
                Operation innerTo = j.getToOperation();
                //means schedule has a cycle, thus its conflict serailizable
                if (outerFrom.equals(innerTo) && outerTo.equals(innerFrom)) {
                    result += "False\n";
                    result += "There is a cycle between transactions: T" + outerFrom.getTransaction() + " and T" + innerFrom.getTransaction();
                    return result;
                }
            }
        }
        result += "True\n";
        result += "Schedule is acyclic, thus it's serializable.\n";
        result += "The schedule is also View-Serializable (Every conflict serializable schedule is also view serializable)";
        return result;
    }

    /**
     * Method returning a flat precedence graph (join the nodes up on paper)
     *
     * @return a string containing nodes of the precedence graph
     */
    public String precedenceGraph() {
        String result = "";
        for (OperationConflict o : conflictSerializableTest()) {
            result += o.fromOperation.getTransaction() + " -> " + o.toOperation.getTransaction() + "\n";
        }

        return result;
    }

    /**
     * Method which examines the given schedule and returns a hashset of type
     * OperationConflict containing node pairs which have been found to conflict
     *
     * @return hashset containing unique node pairs which conflict.
     */
    private HashSet<OperationConflict> conflictSerializableTest() {
        HashSet<String> results = new HashSet<String>();
        HashSet<OperationConflict> results2 = new HashSet<OperationConflict>();

        for (int i = 0; i < theSchedule.size(); i++) {

            Operation outerOperation = theSchedule.get(i);

            for (int j = 0; j < theSchedule.size(); j++) {
                Operation innerOperation = theSchedule.get(j);

                //it's the same transaction, so we don't want to compare
                if ((outerOperation.getTransaction() == innerOperation.getTransaction())) {
                    continue;

                    //both are Reading, thus nothing to note
                } else if (outerOperation.getAction() == 'r' && innerOperation.getAction() == 'r') {
                    continue;
                    //both are on different items, so no impact, continue
                } else if (outerOperation.getItem() != innerOperation.getItem()) {
                    continue;
                }

                //conflict should occur
                else {
                    //if outer operation is before inner operation
                    if (i < j) {
                        //result += outerOperation.getTransaction() + " -> " + innerOperation.getTransaction() + "\n";
                        results.add(outerOperation.getTransaction() + " -> " + innerOperation.getTransaction());
                        results2.add(new OperationConflict(outerOperation, innerOperation));
                        //will change this to else but for test purposes
                    } else if (i > j) {
                        //result += innerOperation.getTransaction() + " -> " + outerOperation.getTransaction() + "\n";
                        results.add(innerOperation.getTransaction() + " -> " + outerOperation.getTransaction());
                        results2.add(new OperationConflict(innerOperation, outerOperation));
                    } else {
                        System.out.println("Shouldn't be printer, ever.");
                    }
                }
            }
        }
        return results2;

    }
}

/**
 * Operation is a class which stores the individual operations of a given schedule.
 * Each operation object stores the action, transaction and the item e.g. r2w.
 * A schedule is simply broken down into individual operations.
 */
class Operation {
    /**
     * A variable which holds the action for the operation (r or w)
     */
    char action;
    /**
     * A variable which holds the transaction number
     */
    int transaction;
    /**
     * A variable which holds the item number
     */
    char item;

    /**
     * Main constructor for the Operation class.
     *
     * @param action      is the action for the operation (r=read or w=write)
     * @param transaction is the transaction number for the operation
     * @param item        that is affected
     */
    Operation(String action, int transaction, String item) {
        this.action = action.charAt(0); //convert string to char
        this.transaction = transaction;
        this.item = item.charAt(0); //convert string to char
    }

    /**
     * A method which returns a char with the action for an Operation
     *
     * @return action of the operation
     */
    public char getAction() {
        return action;
    }

    /**
     * A method which returns an int with the transaction for the Operation
     *
     * @return transaction of the operation
     */
    public int getTransaction() {
        return transaction;
    }

    /**
     * A method which returns a char with the item for an Operation
     *
     * @return item of the operation
     */
    public char getItem() {
        return item;
    }

    /**
     * A method which returns a string representation for an Operation
     *
     * @return operation in a string
     */
    public String toString() {
        return String.valueOf(action) + transaction + String.valueOf(item);
    }

    /* Auto-generated hashcode (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + transaction;
        return result;
    }

    /* Auto-generated equals (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     * Used indirectly by schedule class for distinguishing a set. Only compares transaction
     * since we want ObjectConflict to return unique pairs based on transaction
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Operation other = (Operation) obj;
        if (transaction != other.transaction)
            return false;
        return true;
    }

}

//the class is used to manage a conflict in 2 operations

/**
 * OperationConflict is a class which stores pairs of conflicting operation.
 * Each OperationConflict object contains two Operation objects called from and to
 */
class OperationConflict {
    /**
     * A variable storing an Operation object ( from ->    )
     */
    Operation fromOperation;
    /**
     * A variable storing an Operation object (      -> to )
     */
    Operation toOperation;


    /**
     * Main constructor for OperationConflict class.
     *
     * @param from is the conflicting operation from
     * @param to   is the conflicting operation from points to
     */
    OperationConflict(Operation from, Operation to) {
        this.fromOperation = from;
        this.toOperation = to;
    }

    /**
     * Method which returns the from conflicting Operation
     *
     * @return fromOperation is the start conflicting object
     */
    public Operation getFromOperation() {
        return fromOperation;
    }

    /**
     * Method which returns the to conflicting Operation
     *
     * @return toOperation is the conflicting end object
     */
    public Operation getToOperation() {
        return toOperation;
    }

    /* Auto-generated hash (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((fromOperation == null) ? 0 : fromOperation.hashCode());
        result = prime * result
                + ((toOperation == null) ? 0 : toOperation.hashCode());
        return result;
    }

    /* Auto-generated equals (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     * Used by schedule class to see if there are any equal pairs in the set in the same order.
     * It will also use the Operation equals method
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OperationConflict other = (OperationConflict) obj;
        if (fromOperation == null) {
            if (other.fromOperation != null)
                return false;
        } else if (!fromOperation.equals(other.fromOperation))
            return false;
        if (toOperation == null) {
            if (other.toOperation != null)
                return false;
        } else if (!toOperation.equals(other.toOperation))
            return false;
        return true;
    }
}


