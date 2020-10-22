package core;

import java.util.Observer;
import java.util.Scanner;

public class Main {
    /* You can build the following tree of projects and tasks in order to then pass the tests of
each milestone:
• at the top level, projects software design, software testing, databases and task
transportation
• under software design, projects problems and project time tracker
• under problems, tasks first list and second list
• under project time tracker, tasks read handout and first milestone
     */
    private static void simpleTest(int period, Clock clock) throws InterruptedException {
        //creo el root del projecte per poder crear tasks(1) o intervals(2)
        Project root = new Project("root", "root", null);
        // TODO: Add tags

        Project softwareDesignProject = new Project("Software Design", "Software Design", root);
        Project softwareTestingProject = new Project("Software Testing", "Software Testing", root);
        Project databasesProject = new Project("Databases", "Databases", root);
        Task transportationTask = new Task("Task Transportation", "Task Transportation", root);

        // TODO: Hace esto al crear una instancia de proyecto
        root.inserirFill(softwareDesignProject);
        root.inserirFill(softwareTestingProject);
        root.inserirFill(databasesProject);
        root.inserirFill(transportationTask);

        Project problemsProject = new Project("Problems", "Problems", softwareDesignProject);
        Project timeTrackerProject = new Project("Time Tracker", "Time Tracker", softwareDesignProject);

        softwareDesignProject.inserirFill(problemsProject);
        softwareDesignProject.inserirFill(timeTrackerProject);

        Task firstListTask = new Task("First List", "First List", problemsProject);
        Task secondListTask = new Task("Second List", "Second List", problemsProject);

        problemsProject.inserirFill(firstListTask);
        problemsProject.inserirFill(secondListTask);

        Task readHandoutTask = new Task("Read Handout", "Read Handout", timeTrackerProject);
        Task firstMilestoneTask = new Task("First Milestone", "First Milestone", timeTrackerProject);

        timeTrackerProject.inserirFill(readHandoutTask);
        timeTrackerProject.inserirFill(firstMilestoneTask);

        DataManager dm = DataManager.getInstance();

        dm.importJSONDataToContainer("exportedTimeTrackerData.json");

        //dm.exportData(root);
        //Prints & sc
        Scanner sc = new Scanner(System.in);
        System.out.println("What do you want to do ?");
        System.out.println("1. Create task");
        System.out.println("2. Count time");
        String str = sc.nextLine();

        /**
         * Set the clock period to 2 seconds, which is the time unit we will use but should be
         * configurable. We will make date-times consistent with durations by doing this: each
         * time the user (the code that simulates it) starts a task and later receives a signal from
         * the clock, since the time unit is 2 seconds we will consider that already 2 seconds have
         * passed for this task. Consequently we have to set the new interval duration to 2 seconds,
         * the initial date-time to 2 seconds before the first signal received, and the final date-time
         * to the clock time.
         * Write code to simulate the following user actions that, if you insert the corresponding
         * print sentences in the right places to show only those nodes that change each time, should
         * produce the next output:
         * 1. start task transportation, wait 4 seconds and then stop it
         * 2. wait 2 seconds
         * 3. start task first list, wait 6 seconds
         * 4. start task second list and wait 4 seconds
         * 5. stop first list
         * 6. wait 2 seconds and then stop second list
         * 7. wait 2 seconds
         * 8. start transportation, wait 4 seconds and then stop it
         * To simulate the user waits for some time use Thread.sleep() within a try-catch
         * block.
         */
/**Creem una task al root, a la taskhi assignem un interval i fem que observi durant 10 segons**/
        if (str.equals("1")) {
            System.out.println("equal to 1");

            //Creo la task i interval
            //Task T1 = new Task("T1","Child of root",root);
            //dm.exportData(T1);



            // Transportation
            Interval int1 = new Interval(transportationTask);
            transportationTask.inserirFill(int1);
            System.out.println(transportationTask.getNom() + " starts");

            clock.addObserver(int1);
            int1.update(clock, clock.getDate());
            wait(4);
            clock.deleteObserver(int1);
            System.out.println(transportationTask.getNom() + " stops");


            wait(2);

            // First list
            Interval int2 = new Interval(firstListTask);
            firstListTask.inserirFill(int2);
            System.out.println(firstListTask.getNom() + " starts");

            clock.addObserver(int2);
            int2.update(clock, clock.getDate());
            wait(6);

            // Second list
            Interval int3 = new Interval(secondListTask);
            secondListTask.inserirFill(int3);
            System.out.println(secondListTask.getNom() + " starts");

            clock.addObserver(int3);
            int3.update(clock, clock.getDate());
            wait(4);

            clock.deleteObserver(int2);
            System.out.println(firstListTask.getNom() + " stops");

            wait(2);

            clock.deleteObserver(int3);
            System.out.println(secondListTask.getNom() + " stops");

            wait(2);

            Interval int4 = new Interval(transportationTask);
            transportationTask.inserirFill(int4);
            System.out.println(transportationTask.getNom() + " starts");

            clock.addObserver(int4);
            int4.update(clock, clock.getDate());
            wait(4);

            clock.deleteObserver(int4);
            System.out.println(transportationTask.getNom() + " stops");


            //System.out.println(transportationTask.getNom() + ", durada:" + transportationTask.getDurada());
            //System.out.println(root.nom + ",durada:" + root.getDurada());
        }

        dm.exportDataContainerToJSON(root);


    }

    // temps d'activitat definit del programa en seconds. Fil d'espera
    private static void wait(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final int period = 2;

        Clock clock = new Clock(period);
        Thread thread = new Thread(clock);
        thread.start();/**inicia el run() del clock en un Thread**/

        simpleTest(period, clock);


        clock.stop();

    }


}
