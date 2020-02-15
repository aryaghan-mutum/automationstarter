package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import com.google.gson.JsonElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static com.automationstarter.constants.ServiceConstants.ACTOR1;
import static com.automationstarter.constants.ServiceConstants.ACTOR3;
import static com.automationstarter.constants.ServiceConstants.CAST;
import static com.automationstarter.constants.ServiceConstants.MOVIES;
import static com.automationstarter.constants.ServiceConstants.TITLE;
import static com.automationstarter.services.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static com.automationstarter.services.JsonService.isFieldUndefined;
import static com.automationstarter.services.JsonService.jsonStream;
import static com.automationstarter.services.JsonService.jsonString;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Anurag Muthyam
 */

public class TestLeadActorIsNullForAMovie extends BaseTest {
    
    /**
     * The Test case checks if each actor1 (lead actor) under cast is null in movies_service.json
     * If they are null, the test case logs a list of actor1 associated to the movie title
     * and fails the test
     */
    @Test
    @DisplayName("Find Actor1 That Has Null")
    public void findActor1ThatHasNull() throws FileNotFoundException {
        
        Stream<JsonElement> movies = jsonStream(retrieveMoviesServiceDoc(), MOVIES);
        
        AtomicBoolean isActorNullFound = new AtomicBoolean(false);
        
        movies.forEach(movie -> {
            
            String movieTitle = jsonString(movie, TITLE);
            
            long actor1Count = jsonStream(movie, CAST)
                    .filter(cast -> isActorNull(cast, ACTOR1))
                    .peek(venue -> log(format("actor1 is null for movieTitle: %s", movieTitle)))
                    .count();
            
            if (actor1Count > 0) {
                isActorNullFound.set(true);
            }
        });
        
        if (isActorNullFound.get()) {
            fail();
        }
    }
    
    /**
     * The Test case checks if each actor2 under cast is not null in movies_service.json
     * If they are not null, the test case logs a list of actor3 associated to the movie title
     * and fails the test
     */
    @Test
    @DisplayName("Find Actor3 That Has Null")
    public void findActor3ThatHasNotNull() throws FileNotFoundException {
        
        Stream<JsonElement> movies = jsonStream(retrieveMoviesServiceDoc(), MOVIES);
        
        AtomicBoolean isActor3NotNullFound = new AtomicBoolean(false);
        
        movies.forEach(movie -> {
            
            String movieTitle = jsonString(movie, TITLE);
            
            try {
                String actor3 = jsonStream(movie, CAST)
                        .map(cast -> jsonString(cast, ACTOR3))
                        .reduce((a, b) -> a + "," + b)
                        .get();
    
                long actor3Count = jsonStream(movie, CAST)
                        .filter(cast -> !isActorNull(cast, ACTOR3))
                        .peek(venue -> log(format("actor3 is %s for movieTitle: %s", actor3, movieTitle)))
                        .count();
    
    
                if (actor3Count > 0) {
                    isActor3NotNullFound.set(true);
                }
            }catch (Exception e) {
                log(format("actor3 is null for movieTitle: %s", movieTitle));
            }
            
        });
        
        if (isActor3NotNullFound.get()) {
            fail();
        }
    }
    
    @Step("check if actor is null")
    private boolean isActorNull(JsonElement offering, String actor) {
        return isFieldUndefined(offering, actor) || jsonString(offering, actor) == null;
    }
}
