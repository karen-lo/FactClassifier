package main;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class RecordSerializer implements JsonSerializer{
    @Override
    public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
        Record rec = (Record) o;
        JsonObject jobject = new JsonObject();
        JsonElement jholding = jsonSerializationContext.serialize(rec.getHoldingClassification(), Metadata[].class);
        JsonElement jfacts = jsonSerializationContext.serialize(rec.getFactsClassification(), Metadata[].class);

        jobject.addProperty("file_id", rec.getFile_id());
        jobject.add("holding", jholding);
        jobject.add("facts", jfacts);

        return jobject;
    }
}
