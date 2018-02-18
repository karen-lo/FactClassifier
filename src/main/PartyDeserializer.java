package main;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PartyDeserializer implements JsonDeserializer {

    @Override
    public Party deserialize(JsonElement jsonElement, Type type,
                             JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject j = jsonElement.getAsJsonObject();
        int id = -1;
        String n = null;
        String r = null;
        JsonElement temp;

        temp = j.get("id");
        if(temp != null)  id = temp.getAsInt();
        temp = j.get("name");
        if(temp != null)  n = temp.getAsString();
        temp = j.get("role");
        if(temp != null)  n = temp.getAsString();
        PreviousRole[] pr = jsonDeserializationContext.deserialize(j.get("previousRoles"), PreviousRole[].class);

        return new Party(id, n, r, pr);
    }
}
