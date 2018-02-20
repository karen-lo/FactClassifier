package main;

import java.io.*;
import java.util.*;
import com.google.gson.*;

public class FactClassifier {
    private Record[] records;

    public FactClassifier() {
        this.records = null;
    }

    public void serializeResults(String filename) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filename);
        GsonBuilder gsonBlder = new GsonBuilder();

        gsonBlder.registerTypeAdapter(Record.class, new RecordSerializer());
        gsonBlder.registerTypeAdapter(Metadata.class, new MetadataSerializer());

        Gson gson = gsonBlder.create();

        for(Record record : records) {
            writer.println(gson.toJson(record));
//            System.out.println(gson.toJson(record));
        }

        writer.close();
    }

    public void deseriaizeRecords(String filename) throws FileNotFoundException {
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
            System.out.println("line " + (i+1));
            records[i] = gson.fromJson(record_strings.get(i), Record.class);
        }
    }

    public Record[] getRecords() {
        return records;
    }
}