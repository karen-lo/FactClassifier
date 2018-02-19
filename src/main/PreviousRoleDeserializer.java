package main;

import com.google.gson.*;
import java.lang.reflect.Type;

public class PreviousRoleDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject();
        String r = null;
        String note = null;
        JsonElement temp;

        temp = j.get("role");
        if(temp != null) r = temp.getAsString();
        temp = j.get("caseNote");
        if(temp != null) note = temp.getAsString();
        return new PreviousRole(r, note);
    }
}
