package core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance = null;

    private DataManager() {
    }

    /**
     * Singleton for DataManager, as an external functionality it would be called whenever is needed inside
     * the project. So it's important we are just using a single instance all the time.
     * @return
     */
    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();

        return instance;
    }

    /**
     * Receives a container(root project) and generates the JSON Object which will be used to save it in a json file.
     * Uses recursion to build the json object across all children of root project.
     * @param container
     */
    public void exportDataContainerToJSON(Container container) {
        // Builds the json object recursively.
        JSONObject jsonObject = generateJSONObjectFromContainer(container);
        LocalDateTime now = LocalDateTime.now();
        File file = new File("exportedTimeTrackerData.json");
        FileWriter fileWriter = null;
        // Write it to a file.
        try {
            // Constructs a FileWriter given a file name, using the platform's default charset
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toString());

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Import a json file into a Container object. It uses recursion to extract all the projects and tasks from the
     * json file.
     * @param fileLocation
     * @return
     */
    public Container importJSONDataToContainer(String fileLocation) {
        Container root = null;
        try {

            FileReader reader = new FileReader(fileLocation);
            JSONTokener tokener = new JSONTokener(reader);

            JSONObject object = new JSONObject(tokener);

            // Build to json object recursively.
            root = parseRootProjectData(new Project(), object, null);
            System.out.println("JSON Objects from imported json: ");
            System.out.println(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return root;

    }

    /**
     * Auxiliary function to iterate over the read json file to generate the json object.
     * @param container
     * @param rootJsonObject
     * @param containerFather
     * @return
     */
    private Container parseRootProjectData(Container container, JSONObject rootJsonObject, Container containerFather) {

        String name = rootJsonObject.getString("nom");
        String desc = rootJsonObject.getString("descripcio");
        int duration = 0;
        String initialDate = "";
        String finalDate = "";

        // Make sure to handle null pointers correctly.
        if (rootJsonObject.isNull("durada")) {
            duration = 0;
        } else {
            duration = rootJsonObject.getInt("durada");
            container.setDuration((long) duration);
        }

        if (rootJsonObject.isNull("dataInici")) {
            finalDate = null;
        } else {
            finalDate = rootJsonObject.getString("dataInici");
            container.setFinalDate(LocalDateTime.parse(finalDate));
        }

        if (rootJsonObject.isNull("dataFinal")) {
            initialDate = null;
        } else {
            initialDate = rootJsonObject.getString("dataFinal");
            container.setFinalDate(LocalDateTime.parse(initialDate));
        }

        container.setName(name);
        container.setDescription(desc);
        container.setContainerFather(containerFather);

        if (container instanceof Project) {

            JSONArray jsonArray = rootJsonObject.getJSONArray("fills");
            List<Container> containerChildren = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ob = jsonArray.getJSONObject(i);
                if (ob.has("fills")) {

                    Project p = (Project) parseRootProjectData(new Project(), ob, container);
                    containerChildren.add(p);
                } else {
                    Task t = (Task) parseRootProjectData(new Task(), ob, container);
                    containerChildren.add(t);
                }


            }

            ((Project) container).setContainerChildren(containerChildren);


        } else {
            JSONArray jsonArray = rootJsonObject.getJSONArray("intervals");
            List<Interval> intervalsList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ob = jsonArray.getJSONObject(i);
                int id = ob.getInt("id");
                int durada1 = ob.getInt("durada");
                String dataInici1 = ob.getString("dataInici");
                String dataFinal1 = ob.getString("dataFinal");

                Interval interval = new Interval();
                interval.setFatherTask((Task) container);
                interval.setId(id);
                interval.setDuration((long) durada1);
                interval.setInitialDate(LocalDateTime.parse(dataInici1));
                interval.setFinalDate(LocalDateTime.parse(dataFinal1));

                intervalsList.add(interval);
            }

            ((Task) container).setIntervalList(intervalsList);
        }

        return container;
    }

    /**
     * Auxiliary function to generate json file from container in a recursive manner.
     * @param container
     * @return
     */
    private JSONObject generateJSONObjectFromContainer(Container container) {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("nom", container.getName());
        jsonObject.put("descripcio", container.getDescription() != null ? container.getDescription() : JSONObject.NULL);
        jsonObject.put("durada", container.getDuration() != null ? container.getDuration() : JSONObject.NULL);
        jsonObject.put("dataInici", container.getInitialDate() != null ? container.getInitialDate() : JSONObject.NULL);
        jsonObject.put("dataFinal", container.getFinalDate() != null ? container.getFinalDate() : JSONObject.NULL);

        if (container instanceof Project) {
            List<Container> containerChildren = ((Project) container).getContainerChildren();
            JSONArray jsonArray = new JSONArray();

            for (Container containerChild : containerChildren) {
                JSONObject childData = generateJSONObjectFromContainer(containerChild);
                jsonArray.put(childData);
            }

            jsonObject.put("fills", jsonArray);


        } else {
            List<Interval> intervalList = ((Task) container).getIntervalList();
            JSONArray jsonArray = new JSONArray();

            for (Interval interval : intervalList) {
                JSONObject intervalObject = new JSONObject();
                intervalObject.put("id", interval.getId());
                intervalObject.put("durada", interval.getDuration());
                intervalObject.put("dataInici", interval.getInitialDate() != null ? interval.getInitialDate() : JSONObject.NULL);
                intervalObject.put("dataFinal", interval.getFinalDate() != null ? interval.getFinalDate() : JSONObject.NULL);
                jsonArray.put(intervalObject);
            }

            jsonObject.put("intervals", jsonArray);
        }

        return jsonObject;
    }
}