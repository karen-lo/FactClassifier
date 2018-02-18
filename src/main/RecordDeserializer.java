package main;

import com.google.gson.*;
import netscape.javascript.JSObject;

import java.lang.reflect.Type;

public class RecordDeserializer implements JsonDeserializer {

    @Override
    public Record deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject();
        JsonElement temp;
        String id = null;
        String t = null;
        String[] h = null;
        String[] f = null;

        temp = j.get("file_id");
        if(temp != null) id = temp.getAsString();
        temp = j.get("case_type");
        if(temp != null) t = temp.getAsString();

        temp = j.get("holding");
        if(temp != null) {
            JsonObject json_holding = temp.getAsJsonObject();
            temp = json_holding.get("sentences");
            if(temp != null) {
                JsonArray json_sentences = temp.getAsJsonArray();

                h = new String[json_sentences.size()];
                for(int i=0; i<h.length; i++) {
                    JsonElement sentence = json_sentences.get(i);
                    h[i] = sentence.getAsString();
                }
            }
        }

        temp = j.get("facts");
        if(temp != null) {
            JsonObject json_facts = temp.getAsJsonObject();
            temp = json_facts.get("fact");
            if(temp != null) {
                JsonArray json_fact = temp.getAsJsonArray();

                f = new String[json_fact.size()];
                for(int i=0; i<f.length; i++) {
                    JsonElement fact = json_fact.get(i);
                    f[i] = fact.getAsString();
                }
            }

        }

        Party[] parties = jsonDeserializationContext.deserialize(j.get("parsed_parties"), Party[].class);

        return new Record(id, t, h, f, parties);
    }
}
