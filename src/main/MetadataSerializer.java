package main;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MetadataSerializer implements JsonSerializer {
    @Override
    public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
        Metadata m = (Metadata) o;
        JsonObject jobject = new JsonObject();
        jobject.addProperty("root_term", m.getRootTerm());
        jobject.addProperty("relationship", m.getRelationship());
        jobject.addProperty("subject_term", m.getNsubj());
        jobject.addProperty("subject", m.getClassification());
        return jobject;
    }
}
