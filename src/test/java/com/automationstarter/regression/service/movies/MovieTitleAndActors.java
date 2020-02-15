package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.automationstarter.constants.ServiceConstants.ACTOR1;
import static com.automationstarter.constants.ServiceConstants.ACTOR2;
import static com.automationstarter.constants.ServiceConstants.CAST;
import static com.automationstarter.constants.ServiceConstants.MOVIES;
import static com.automationstarter.constants.ServiceConstants.TITLE;
import static com.automationstarter.services.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static com.automationstarter.services.JsonService.jsonStream;
import static com.automationstarter.services.JsonService.jsonString;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Anurag Muthyam
 */

public class MovieTitleAndActors extends BaseTest {
    
    /**
     * 1. Get actor1 and actor2 from movies.service.json
     * 2. Store them in a list
     * 3. Assert
     */
    @Test
    @DisplayName("Test Movie Title And Actors")
    public void testMovieTitleAndActorsProcedure1() throws FileNotFoundException {

        Map<String, List<String>> movieMap = new HashMap<>();
    
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = jsonString(movie, TITLE);
    
                    String actor1 = getActor(movie, ACTOR1);
                    String actor2 = getActor(movie, ACTOR2);
    
                    List<String> actors = asList(actor1, actor2);
    
                    movieMap.put(movieTitle, actors);
                });
    
        assertEquals(movieMap.keySet().size(), 5);
        
        movieMap.keySet().stream().anyMatch(movieName -> movieName.equalsIgnoreCase("casino"));
        movieMap.keySet().stream().anyMatch(movieName -> movieName.equalsIgnoreCase("amadeus"));
        
        movieMap.entrySet().stream().collect(toList());
    }
    
    @Test
    @DisplayName("Test Movie Title And Actors")
    public void testMovieTitleAndActorsProcedure2() throws FileNotFoundException {
    
        Map<String, List<String>> movieMap =
                jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .collect(toMap(
                        movie -> jsonString(movie, TITLE),
                        movie -> asList(getActor(movie, ACTOR1), getActor(movie, ACTOR2))));
        
        assertEquals(movieMap.keySet().size(), 5);
    
        movieMap.keySet().stream().anyMatch(movieName -> movieName.equalsIgnoreCase("casino"));
        movieMap.keySet().stream().anyMatch(movieName -> movieName.equalsIgnoreCase("amadeus"));
    
        movieMap.entrySet().stream().collect(toList());
    }
    
    private String getActor(JsonElement movie, String actor12) {
        return jsonStream(movie, CAST)
                .filter(cast -> jsonString(cast, actor12) != null)
                .map(cast -> jsonString(cast, actor12))
                .reduce((a, b) -> a + "," + b)
                .get();
    }
    
    
}
