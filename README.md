# Conflict Serializability Algorithm

I developed this brute force algorithm during my masters studies. It's a great aid to quickly determine whether a schedule is serializable and draw a precendence graph.  

**Features:**

* Can handle any size schedule
* Can test for **conflict Serializability** 
* Can draw a simple **precedence graph**
* Provides some feedback and basic explanation about the schedule
* It will specify iff it is **view serializable** (will only mention if it knows, but there are no set methods) 

Schedule Format:
------
The app takes a schedule in the form of: ```"r1x", "r2z", "r1z", "r3y", "r3y",...```

Let's look at the first instructions:
* ```r1x```: r = read, 1 = the transaction, x is the element

Let *z* be an arbitrary instruction, then:
* ```z[0]```: can take the form of ```r``` (read) or ```w``` (write) to set the operation
* ```z[1]```: can take the form of ```1,2,3,...,n``` to set the transaction
* ```z[2]```: can take the form of ```a,b,c,...,z``` to set the element

**Note:** Schedule **MUST** be spaced exactly like examples:```"r1x", "r2z",...```. This will **NOT** work: ```"r1x","r2z",...```

Usage (from IDE):
------

1. Create a schedule: ```String[] schedule = {"r1x", "r2z", "r1z", "r3y", "r3y", "w1x", "w3y", "r2y", "w2z", "w2y"};```
2. Create an instance of Schedule (passing in schedule as parameter): ```Schedule s = new Schedule(schedule);```
3. Can call one of three methods:
    * ```s.getSchedule()```: returns the schedule 
    * ```s.precedenceGraph()```: returns a graphical representation of the precedence graph
    * ```s.conflictSerializableSolution()```: returns information on the schedule
    
    
**Input:**
```java
  String[] schedule = {"r1x", "r2z", "r1z", "r3y", "r3y", "w1x", "w3y", "r2y", "w2z", "w2y"};
  Schedule s = new Schedule(schedule);
  
  System.out.println(s.getSchedule());
  System.out.println(s.precedenceGraph());
  System.out.println(s.conflictSerializableSolution());
```

**Output:**
```java
  //getSchedule()
  r1x, r2z, r1z, r3y, r3y, w1x, w3y, r2y, w2z, w2y, 
  
  //precedenceGraph()
  3 -> 2
  1 -> 2

  //conflictSerializableSolution()
  Is Schedule Conflict-Serializable: True
  Schedule is acyclic, thus it's serializable.
  The schedule is also View-Serializable (Every conflict serializable schedule is also view serializable)
```

Usage (from Console):
------
1. Open CMD and type: ```java -jar serializability.jar```
2. You now have two options to add as a parameter:
    * ```test```: provides you with a test case with an explanation on how to use program 
    * ```<your-schedule>```: returns the result for your schedule

**Input:**

 ```java 
 
 //run a sample program
 java -jar serializability.jar test
 
 //test with user-specified schedule
 java -jar serializability.jar "r1x", "r2z", "r1z", "r3y", "r3y", "w1x", "w3y", "r2y", "w2z", "w2y"
  ```
  
  **Output:**
```java

//java -jar serializability.jar test
To use the app, simple open command-prompt in the location of the serializability.jar file and add a schedule like shown below: 
  ...
  ...

//java -jar serializability.jar "r1x", "r2z", "r1z", "r3y", "r3y", "w1x", "w3y", "r2y", "w2z", "w2y"
The schedule: r1x, r2z, r1z, r3y, r3y, w1x, w3y, r2y, w2z, w2y,

Precedence Graph:
3 -> 2
1 -> 2

Is Schedule Conflict-Serializable: True
Schedule is acyclic, thus it's serializable.
The schedule is also View-Serializable (Every conflict serializable schedule is
also view serializable)


```
  
  
