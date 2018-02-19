package main;

import java.io.*;
import java.util.*;
import com.google.gson.*;

public class FactClassifier {
    private Record[] records;

    public FactClassifier() {
        this.records = null;
    }

    public void setRecords(String filename) throws FileNotFoundException {
        File f = new File(filename);
        ArrayList<String> record_strings = new ArrayList<>();

        try(BufferedReader br = new BufferedReader( new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                record_strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBldr = new GsonBuilder();

        gsonBldr.registerTypeAdapter(Record.class, new RecordDeserializer());
        gsonBldr.registerTypeAdapter(Party.class, new PartyDeserializer());
        gsonBldr.registerTypeAdapter(PreviousRole.class, new PreviousRoleDeserializer());

        Gson gson = gsonBldr.create();

        this.records = new Record[record_strings.size()];

        for(int i=0; i<record_strings.size(); i++) {
            records[i] = gson.fromJson(record_strings.get(i), Record.class);
        }
    }

    public Record[] getRecords() {
        return records;
    }
}