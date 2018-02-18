package main;

import java.io.*;
import java.util.*;
import com.google.gson.*;

public class FactClassifier {
    static GsonBuilder gsonBldr;
    static Gson gson;

    public static void main(String[] args) throws java.io.FileNotFoundException {
        File f = new File(args[0]);
        ArrayList<String> record_strings = new ArrayList<>();

        try(BufferedReader br = new BufferedReader( new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                record_strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        gsonBldr = new GsonBuilder();

        gsonBldr.registerTypeAdapter(Record.class, new RecordDeserializer());
        gsonBldr.registerTypeAdapter(Party.class, new PartyDeserializer());
        gsonBldr.registerTypeAdapter(PreviousRole.class, new PreviousRoleDeserializer());

        gson = gsonBldr.create();

        for(int i=0; i<record_strings.size(); i++) {
            Record r = gson.fromJson(record_strings.get(i), Record.class);
            System.out.println(r.getFile_id());
        }
    }

}