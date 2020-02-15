package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.automationstarter.constants.ServiceConstants.DATE_RELEASED;
import static com.automationstarter.constants.ServiceConstants.MOVIES;
import static com.automationstarter.constants.ServiceConstants.TITLE;
import static com.automationstarter.services.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static com.automationstarter.services.JsonService.jsonStream;
import static com.automationstarter.services.JsonService.jsonString;
import static com.automationstarter.utils.GeneralUtil.convertStringToLocalDateFormat;
import static com.automationstarter.utils.GeneralUtil.getCurrentYear;

/**
 * @author Anurag Muthyam
 */

public class TestMoviesReleasedLessThan40Years extends BaseTest {
    
    /**
     * Approach 1 using forEach() :
     * 1. Gets movieTitle for each movie
     * 2. Gets yearReleased for each movie
     * 3. Calculates the difference between the (currentYear - yearReleased)
     * 4. If yearReleased < 40 then adds the movieTitle to the list
     */
    @Test
    @DisplayName("Test Total Count Of Movies Released Less Than 40 Years From Current Year")
    public void testTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYear() throws FileNotFoundException {
        
        int countForProcedure1 = getTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYearProcedure1();
        int countForProcedure2 = getTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYearProcedure2();
        
        Assertions.assertEquals(countForProcedure1, countForProcedure2);
        
    }
    
    @Step("Approach 1 using forEach(): Get Total Count Of Movies Released Less Than 40 Years From Current Year Procedure 1")
    public int getTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYearProcedure1() throws FileNotFoundException {
        
        List<String> moviesReleasedLessThan40YearsFromTodayList = new ArrayList<>();
        
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = jsonString(movie, TITLE);
                    
                    int yearReleased = convertStringToLocalDateFormat(jsonString(movie, DATE_RELEASED)).getYear();
                    int yearDifference = getCurrentYear() - yearReleased;
                    
                    if (yearDifference < 40) {
                        log(String.format("movieTitle: %s was released %s year ago", movieTitle, yearDifference));
                        moviesReleasedLessThan40YearsFromTodayList.add(movieTitle);
                    }
                    
                });
        
        return moviesReleasedLessThan40YearsFromTodayList.size();
    }
    
    @Step("Approach 2 using map(), filter() and count(): Get Total Count Of Movies Released Less Than 40 Years From Current Year Procedure 1")
    public int getTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYearProcedure2() throws FileNotFoundException {
    
        long moviesReleasedLessThan40YearsFromTodayCount = jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .map(movie -> jsonString(movie, DATE_RELEASED))
                .map(movie -> convertStringToLocalDateFormat(movie).getYear())
                .map(movie -> getCurrentYear() - movie)
                .filter(yearDifference -> yearDifference < 40)
                .count();
        
        log(String.format("There are %s movies released 40 years ago", moviesReleasedLessThan40YearsFromTodayCount));
        
        return (int) moviesReleasedLessThan40YearsFromTodayCount;
    }
    
}
