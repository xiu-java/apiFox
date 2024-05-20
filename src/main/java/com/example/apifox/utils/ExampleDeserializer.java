package com.example.apifox.utils;

import com.example.apifox.model.Example;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ExampleDeserializer implements JsonDeserializer<Example> {
    @Override
    public Example deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Example model = new Example();
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            model.setFieldObject(context.deserialize(jsonObject, Map.class));
        } else if(json.isJsonArray()){
            JsonArray jsonArray = json.getAsJsonArray();
            List<Map<String, String>> jsonList =  context.deserialize(jsonArray, new TypeToken<List<Map<String, String>>>(){}.getType());
            model.setFieldList(jsonList);
        }else if(json.isJsonPrimitive()) {
            model.setFieldSting(json.getAsString());
        }
        return model;
    }
}
