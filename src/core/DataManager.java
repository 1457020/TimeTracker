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

    private DataManager(){}

    public static DataManager getInstance()
    {
        if (instance == null)
            instance = new DataManager();

        return instance;
    }

    public void exportDataContainerToJSON(Container container){
        JSONObject jsonObject = generateJSONObjectFromContainer(container);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(jsonObject);
        File file = new File("exportedTimeTrackerData.json");
        FileWriter fileWriter = null;
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

    public Container importJSONDataToContainer(String fileLocation){
        Project root = null;
        try {

            FileReader reader = new FileReader(fileLocation);
            JSONTokener tokener = new JSONTokener(reader);

            JSONObject object = new JSONObject(tokener);
            //root = new Project(object.getString("nom"), object.getString("descripcio"), null);

            Container c = parseRootProjectData(new Project(), object, null);
            System.out.println("Parsed json to object");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return root;

    }

    private Container parseRootProjectData(Container container, JSONObject rootJsonObject, Container containerPare){

        String nom = rootJsonObject.getString("nom");
        String desc = rootJsonObject.getString("descripcio");
        int durada = 0;
        String dataFinal = "";
        String dataInici = "";
        if (rootJsonObject.isNull("durada")){
            durada = 0;
        } else {
            durada = rootJsonObject.getInt("durada");
            container.setDurada((long) durada);
        }


        if (rootJsonObject.isNull("dataInici")){
            dataInici = null;
        } else {
            dataInici = rootJsonObject.getString("dataInici");
            container.setTempsFinal(LocalDateTime.parse(dataInici));
        }

        if (rootJsonObject.isNull("dataFinal")){
            dataFinal = null;
        } else {
            dataFinal = rootJsonObject.getString("dataFinal");
            container.setTempsFinal(LocalDateTime.parse(dataFinal));
        }

        container.setNom(nom);
        container.setDescripcio(desc);
        container.setPare(containerPare);

        if (container instanceof Project){

            JSONArray jsonArray = rootJsonObject.getJSONArray("fills");
            List<Container> llistaFills= new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject ob = jsonArray.getJSONObject(i);
                if (ob.has("fills")){

                    Project p = (Project) parseRootProjectData(new Project(), ob, container);
                    llistaFills.add(p);
                } else {
                    Task t = (Task) parseRootProjectData(new Task(), ob, container);
                    llistaFills.add(t);
                }


            }

            ((Project) container).setLlistaFills(llistaFills);



        } else {
            JSONArray jsonArray = rootJsonObject.getJSONArray("intervals");
            List<Interval> llistaIntervals= new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject ob = jsonArray.getJSONObject(i);
                int id = ob.getInt("id");
                int durada1 = ob.getInt("durada");
                String dataInici1 = ob.getString("dataInici");
                String dataFinal1 = ob.getString("dataFinal");

                Interval interval = new Interval();
                interval.setPare((Task) container);
                interval.setId(id);
                interval.setDurada(durada1);
                interval.setDataInici(LocalDateTime.parse(dataInici1));
                interval.setDataFinal(LocalDateTime.parse(dataFinal1));

                llistaIntervals.add(interval);
            }

            ((Task) container).setLlistaIntervals(llistaIntervals);
        }

        return container;
    }

    private JSONObject generateJSONObjectFromContainer(Container container){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("nom", container.getNom());
        jsonObject.put("descripcio", container.getDescripcio() != null ? container.getDescripcio() : JSONObject.NULL);
        jsonObject.put("durada", container.getDurada() != null ? container.getDurada() : JSONObject.NULL);
        jsonObject.put("dataInici", container.getTempsInici() != null ? container.getTempsInici() : JSONObject.NULL);
        jsonObject.put("dataFinal", container.getTempsFinal() != null ? container.getTempsFinal() : JSONObject.NULL);

        if (container instanceof Project){
            List<Container> llistaFills= ((Project) container).getLlistaFills();
            JSONArray jsonArray = new JSONArray();

            for (Container containerFill : llistaFills){
                JSONObject fillData = generateJSONObjectFromContainer(containerFill);
                jsonArray.put(fillData);
            }

            jsonObject.put("fills", jsonArray);


        } else {
            List<Interval> llistaIntervals= ((Task)container).getLlistaIntervals();
            JSONArray jsonArray = new JSONArray();

            for (Interval interval : llistaIntervals){
                JSONObject intervalObject = new JSONObject();
                intervalObject.put("id", interval.getId());
                intervalObject.put("durada", interval.getDurada());
                intervalObject.put("dataInici", interval.getDataInici() != null ? interval.getDataInici() : JSONObject.NULL);
                intervalObject.put("dataFinal", interval.getDataFinal() != null ? interval.getDataFinal() : JSONObject.NULL);
                jsonArray.put(intervalObject);
            }

            jsonObject.put("intervals", jsonArray);
        }

        /*else {*/
        return jsonObject;
        //}
    }
}
