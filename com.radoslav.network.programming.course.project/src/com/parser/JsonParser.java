package com.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.parser.JSONParser;

public class JsonParser {

    public static String toJson(Object object) {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(object);
    }

}
