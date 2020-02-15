package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.automationstarter.constants.ServiceConstants.COUNTRY_RELEASED;
import static com.automationstarter.constants.ServiceConstants.MOVIES;
import static com.automationstarter.constants.ServiceConstants.MOVIE_ITEM;
import static com.automationstarter.constants.ServiceConstants.MOVIE_RELEASE;
import static com.automationstarter.constants.ServiceConstants.MOVIE_RELEASED_PRICE;
import static com.automationstarter.constants.ServiceConstants.MOVIE_RELEASED_STATE;
import static com.automationstarter.constants.ServiceConstants.TITLE;
import static com.automationstarter.services.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static com.automationstarter.services.JsonService.isFieldUndefined;
import static com.automationstarter.services.JsonService.jsonStream;
import static com.automationstarter.services.JsonService.jsonString;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.platform.commons.util.StringUtils.isBlank;

/**
 * @author Anurag Muthyam
 */

public class TestMovieReleasedStateAndMoviePrice extends BaseTest {
    
    /**
     * The Test case validates 'movieReleasedState' & 'moveReleasedPrice' are both null/empty associated to each
     * 'countryReleased' and for each 'movieTitle'
     * If both 'movieReleasedState' & 'moveReleasedPrice' are null/empty, then the test fails
     */
    @Test
    @DisplayName("Test MovieReleasedState And MoveReleasedPrice Null Or Missing")
    public void testMovieReleasedStateAndMoveReleasedPriceNullOrMissing() throws FileNotFoundException {
        
        AtomicBoolean isMovieReleasedStateAndMoveReleasedPriceNullOrMissing = new AtomicBoolean(false);
        
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = jsonString(movie, TITLE);
                    
                    jsonStream(movie, MOVIE_RELEASE)
                            .forEach(movieRelease -> {
                                
                                String countryReleased = jsonString(movieRelease, COUNTRY_RELEASED);
                                
                                jsonStream(movieRelease, MOVIE_ITEM)
                                        .forEach(movieItem -> {
                                            
                                            boolean isMovieReleasedStateFieldMissing = isFieldUndefined(movieItem, MOVIE_RELEASED_STATE);
                                            boolean isMoveReleasedPriceFieldMissing = isFieldUndefined(movieItem, MOVIE_RELEASED_PRICE);
                                            
                                            if (!isMovieReleasedStateFieldMissing &&
                                                    !isMoveReleasedPriceFieldMissing &&
                                                    isBlank(jsonString(movieItem, MOVIE_RELEASED_STATE)) &&
                                                    isBlank(jsonString(movieItem, MOVIE_RELEASED_PRICE))) {
                                                
                                                isMovieReleasedStateAndMoveReleasedPriceNullOrMissing.set(true);
                                                log(format("ERROR: movieReleasedState & moveReleasedPrice are both null/empty "
                                                                + "under countryReleased: %s for "
                                                                + "movieTitle: %s",
                                                        countryReleased,
                                                        movieTitle));
                                            } else if (isMovieReleasedStateFieldMissing && isMoveReleasedPriceFieldMissing) {
                                                log(format("WARN: movieReleasedState & moveReleasedPrice field are both "
                                                                + "missing under countryReleased: %s for "
                                                                + "movieTitle: %s",
                                                        countryReleased,
                                                        movieTitle));
                                            }
                                            
                                        });
                            });
                    
                });
        
        if (isMovieReleasedStateAndMoveReleasedPriceNullOrMissing.get()) {
            fail();
        }
    }
}
