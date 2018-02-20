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
        jobject.addProperty("subject", m.getSubject());
        jobject.addProperty("subject_name", m.getSubjectName());
        return jobject;
    }
}
