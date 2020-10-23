package core;

public class Main {

    /**
     * Apendix A and B test for First milestone.
     * @param period
     * @param clock
     * @throws InterruptedException
     */
    private static void simpleTest(Long period, Clock clock) throws InterruptedException {
        // Create the projects and tasks tree structure from Apendix A
        Project root = new Project("root", "root", null);

        Project softwareDesignProject = new Project("Software Design", "Software Design", root);
        Project softwareTestingProject = new Project("Software Testing", "Software Testing", root);
        Project databasesProject = new Project("Databases", "Databases", root);
        Task transportationTask = new Task("Task Transportation", "Task Transportation", root);

        root.addChild(softwareDesignProject);
        root.addChild(softwareTestingProject);
        root.addChild(databasesProject);
        root.addChild(transportationTask);

        Project problemsProject = new Project("Problems", "Problems", softwareDesignProject);
        Project timeTrackerProject = new Project("Time Tracker", "Time Tracker", softwareDesignProject);

        softwareDesignProject.addChild(problemsProject);
        softwareDesignProject.addChild(timeTrackerProject);

        Task firstListTask = new Task("First List", "First List", problemsProject);
        Task secondListTask = new Task("Second List", "Second List", problemsProject);

        problemsProject.addChild(firstListTask);
        problemsProject.addChild(secondListTask);

        Task readHandoutTask = new Task("Read Handout", "Read Handout", timeTrackerProject);
        Task firstMilestoneTask = new Task("First Milestone", "First Milestone", timeTrackerProject);

        timeTrackerProject.addChild(readHandoutTask);
        timeTrackerProject.addChild(firstMilestoneTask);

        wait((int) 4.5);

        // Call DataManager tool in a singleton way to export and import the tree structure.
        DataManager dm = DataManager.getInstance();

        // Transportation
        System.out.println(transportationTask.getName() + " starts");
        transportationTask.startInterval(clock, true);

        wait(4);

        transportationTask.pararInterval(clock);
        System.out.println(transportationTask.getName() + " stops");

        wait(2);

        // First list
        firstListTask.startInterval(clock, false);
        System.out.println(firstListTask.getName() + " starts");
        wait(6);

        // Second list
        secondListTask.startInterval(clock, false);
        System.out.println(secondListTask.getName() + " starts");
        wait(4);

        firstListTask.pararInterval(clock);
        System.out.println(firstListTask.getName() + " stops");
        wait(2);

        secondListTask.pararInterval(clock);
        System.out.println(secondListTask.getName() + " stops");
        wait(2);

        // Transportation task
        transportationTask.startInterval(clock, false);
        System.out.println(transportationTask.getName() + " starts");
        wait(4);

        transportationTask.pararInterval(clock);
        System.out.println(transportationTask.getName() + " stops");

        // Persistence functionality. Export the root project in a json file
        dm.exportDataContainerToJSON(root);
        System.out.println("Exported json file generated: exportedTimeTrackerData.json");
        /* Persistence functionality. Import the json file into container object which contains exactly
           the root project information*/
        Container importedRootProject = dm.importJSONDataToContainer("exportedTimeTrackerData.json");
        System.out.println("Root project name: " + importedRootProject.getName() + " || Root project Duration: " + importedRootProject.getDuration());
    }

    private static void wait(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // For testing purposes. Each period has 2 seconds.
        final Long period = 2L;
        Clock clock = new Clock(period);
        Thread thread = new Thread(clock);
        // Starts clock
        thread.start();
        simpleTest(period, clock);
        clock.stop();
    }


}
