package com.manu.baselibrary.http.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Date;

public class GsonUtil {

    protected static Gson gson;

    public static synchronized Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        @Override
                        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            return new Date(json.getAsJsonPrimitive().getAsLong());
                        }
                    })
                    .serializeNulls()
                    .create();
        }
        return gson;
    }

    public static String getValueFromJSONObject(JSONObject json, String name) {
        String result = "";
        if (json.has(name) && !json.isNull(name)) {
            try {
                result = String.valueOf(json.get(name));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
