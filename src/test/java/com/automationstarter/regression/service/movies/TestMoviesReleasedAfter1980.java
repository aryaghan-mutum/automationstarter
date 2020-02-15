package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.automationstarter.constants.ServiceConstants.DATE_RELEASED;
import static com.automationstarter.constants.ServiceConstants.MOVIES;
import static com.automationstarter.constants.ServiceConstants.TITLE;
import static com.automationstarter.constants.ServiceConstants.YEAR_RELEASED;
import static com.automationstarter.services.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static com.automationstarter.services.JsonService.jsonInt;
import static com.automationstarter.services.JsonService.jsonStream;
import static com.automationstarter.services.JsonService.jsonString;
import static com.automationstarter.utils.GeneralUtil.convertStringToLocalDateFormat;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Anurag Muthyam
 */

public class TestMoviesReleasedAfter1980 extends BaseTest {
    
    /**
     * Operations used: foreach():
     * 1. Gets movieTitle for each movie
     * 2. Gets yearReleased for each movie
     * 3. If yearReleased > 1980 then add the movieTitle to a list
     * 4. If movieTitle doesn't contain "Amadeus" then FAIL
     */
    @Test
    @DisplayName("Get List Of Movies Released After 1980")
    public void getListOfMoviesReleasedAfter1980() throws FileNotFoundException {
        
        List<String> moviesReleasedAfter1980List = new ArrayList<>();
        
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = jsonString(movie, TITLE);
                    
                    int yearReleased = convertStringToLocalDateFormat(jsonString(movie, DATE_RELEASED)).getYear();
                    
                    if (yearReleased > 1980) {
                        moviesReleasedAfter1980List.add(movieTitle);
                    }
                    
                });
        
        if (!moviesReleasedAfter1980List.contains("Amadeus")) {
            fail();
        }
    }
    
    /**
     * Operations used: map(), filter() and collect():
     * 1. Gets yearReleased for each movie
     * 3. If yearReleased > 1980 then add the yearReleased to a list
     * 4. If moviesReleasedAfter1980List.size() != 3 then FAIL
     * 5. If yearReleased != 1984 then FAIL
     */
    @Test
    @DisplayName("Get List Of Movies Released Years After 1980")
    public void getListOfMoviesReleasedYearsAfter1980() throws FileNotFoundException {
        
        List<Integer> moviesReleasedAfter1980List = jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .map(movie -> jsonInt(movie, YEAR_RELEASED))
                .filter(yearReleased -> yearReleased > 1980)
                .collect(toList());
        
        Assertions.assertEquals(moviesReleasedAfter1980List.size(), 3);
        
        if (!moviesReleasedAfter1980List.contains(1984)) {
            fail();
        }
    }
    
}
