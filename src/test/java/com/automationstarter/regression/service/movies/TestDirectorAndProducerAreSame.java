package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import com.google.gson.JsonElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.automationstarter.constants.ServiceConstants.DIRECTOR;
import static com.automationstarter.constants.ServiceConstants.MOVIES;
import static com.automationstarter.constants.ServiceConstants.PRODUCER;
import static com.automationstarter.constants.ServiceConstants.TITLE;
import static com.automationstarter.services.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static com.automationstarter.services.JsonService.jsonStream;
import static com.automationstarter.services.JsonService.jsonString;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author Anurag Muthyam
 */

public class TestDirectorAndProducerAreSame extends BaseTest {
    
    /**
     * The Test case validates if a director for a movie is also a producer.
     * 1. Gets a list of directors for each movie from movies_service.json
     * 2. Gets a list of producers for each movie from movies_service.json
     * 3. If yhe director for a movie is also a producer then collects them in a Set.
     */
    @Test
    @DisplayName("Test Director And Producer Are Same For A Movie")
    public void testDirectorAndProducerAreSameForAMovie() throws FileNotFoundException {
        Assertions.assertEquals(checkIfDirectorAndProducerAreSameForAMovieProcedure1(), checkIfDirectorAndProducerAreSameForAMovieProcedure2());
    }
    
    @Step("Approach 1 using foreach():")
    public Set<Set<String>> checkIfDirectorAndProducerAreSameForAMovieProcedure1() throws FileNotFoundException {
        
        Set<Set<String>> directorIsProducerForMovieSet = new HashSet<>();
        
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = jsonString(movie, TITLE);
                    
                    // get a list of directors for a  movie
                    List<String> directorList = jsonStream(movie, DIRECTOR)
                            .map(JsonElement::getAsString)
                            .collect(toList());
                    
                    // get a list of producers for a  movie
                    List<String> producerList = jsonStream(movie, PRODUCER)
                            .map(JsonElement::getAsString)
                            .collect(toList());
                    
                    directorIsProducerForMovieSet
                            .add(directorList
                                    .stream()
                                    .filter(director -> producerList.contains(director))
                                    .peek(director -> log(format("Director: %s is also a Producer for a movie: %s", director, movieTitle)))
                                    .map(mT -> movieTitle)
                                    .collect(toSet()));
                    
                });
        
        return directorIsProducerForMovieSet;
    }
  
    @Step("Approach 2 using forEach(), map(), filter() and collect()")
    public Set<Set<String>> checkIfDirectorAndProducerAreSameForAMovieProcedure2() throws FileNotFoundException {
        
        Set<Set<String>> directorIsProducerForMovieSet = new HashSet<>();
    
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(movie -> directorIsProducerForMovieSet
                        .add(jsonStream(movie, DIRECTOR)
                                .map(JsonElement::getAsString)
                                .filter(director -> getListOfProducersForAMovie(movie).contains(director))
                                .peek(director -> log(format("Director: %s is also a Producer for a movie: %s", director, jsonString(movie, TITLE))))
                                .map(mT -> jsonString(movie, TITLE))
                                .collect(toSet())));
        
        return directorIsProducerForMovieSet;
    }
    
    @Step("Get a list of producers for a movie")
    private List<String> getListOfProducersForAMovie(JsonElement movie) {
        return jsonStream(movie, PRODUCER)
                .map(JsonElement::getAsString)
                .collect(toList());
    }
}
