package com.automationstarter.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static com.automationstarter.constants.ServiceConstants.MOVIE_SERVICE_PATH;

public class JsonPayloadWorkflow {
    
    public static JsonElement retrieveMoviesServiceDoc() throws FileNotFoundException {
        return retrieveServiceDocument(MOVIE_SERVICE_PATH);
    }
    
    private static JsonElement retrieveServiceDocument(String servicePath) throws FileNotFoundException {
        FileReader reader = new FileReader(servicePath);
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(reader);
    }
    
    
}
