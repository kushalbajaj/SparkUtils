package com.sparkutilities.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Util {

    public static  <T> T getMappedObjectFromJson(String str,Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = str.replaceFirst(";","");
        System.out.println(s);
        JSONArray jsonArray = new JSONArray(s);
        JSONObject jsonElement = jsonArray.getJSONObject(0);
        T obj = null;
        try {
            obj = objectMapper.readValue(jsonElement.toString(), clazz);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
