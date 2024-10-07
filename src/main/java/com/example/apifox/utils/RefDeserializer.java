package com.example.apifox.utils;

import com.example.apifox.model.Detail;
import com.example.apifox.model.Item;
import com.example.apifox.model.MethodType;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class RefDeserializer implements JsonDeserializer<Item> {
    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Item model = new Item();
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                Detail detail  = context.deserialize(value, Detail.class);
                detail.setMethod(MethodType.fromString(key));
                model.add(detail);
            }
        }
        return model;
    }
}
