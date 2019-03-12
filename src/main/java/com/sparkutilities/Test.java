package com.sparkutilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.io.IOException;

public class Test {

    public static void main(String args[]) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        JSONArray jsonArray = new JSONArray("");
System.out.println(jsonArray);
    }
}
