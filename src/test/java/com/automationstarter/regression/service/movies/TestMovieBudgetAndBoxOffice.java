package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static com.automationstarter.constants.ServiceConstants.BOX_OFFICE;
import static com.automationstarter.constants.ServiceConstants.BUDGET;
import static com.automationstarter.constants.ServiceConstants.MOVIES;
import static com.automationstarter.constants.ServiceConstants.TITLE;
import static com.automationstarter.services.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static com.automationstarter.services.JsonService.jsonStream;
import static com.automationstarter.services.JsonService.jsonString;
import static org.jsoup.helper.StringUtil.isBlank;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Anurag Muthyam
 */

public class TestMovieBudgetAndBoxOffice extends BaseTest {
    
    /**
     * Test budget and boxOffice of the movies
     * -> If budget, and boxOffice are null or empty, it is fine.
     * -> If budget or boxOffice is null or empty, the test case FAILS
     */
    @Test
    @DisplayName("Test Budget And BoxOffice Are Null Or Empty For Movies")
    public void testBudgetAndBoxOfficeAreNullOrEmptyForMovies() throws FileNotFoundException {
        
        Stream<JsonElement> movies = jsonStream(retrieveMoviesServiceDoc(), MOVIES);
        
        AtomicBoolean isCostFieldNull = new AtomicBoolean(false);
        
        movies.forEach(movie -> {
            
            String movieTitle = jsonString(movie, TITLE);
            String budget = jsonString(movie, BUDGET);
            String boxOffice = jsonString(movie, BOX_OFFICE);
            
            if (isBlank(budget) || isBlank(boxOffice)) {
                
                // if budget and boxOffice are all null, then skip.
                if (isBlank(budget) && isBlank(boxOffice)) {
                    isCostFieldNull.set(false);
                }
                // if budget or boxOffice is null or empty, the test case fails.
                else {
                    if (isBlank(budget)) {
                        isCostFieldNull.set(true);
                        log(String.format("budget: is null or empty for movieTitle: %s ", movieTitle));
                    }
                    if (isBlank(boxOffice)) {
                        isCostFieldNull.set(true);
                        log(String.format("boxOffice: is null or empty for movieTitle: %s", movieTitle));
                    }
                }
            }
        });
        
        if (isCostFieldNull.get()) {
            fail();
        }
    }
    
}
