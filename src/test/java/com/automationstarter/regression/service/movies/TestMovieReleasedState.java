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

public class TestMovieReleasedState extends BaseTest {
    
    /**
     * Procedure 1 using isFieldUndefined():
     * The Test case validates 'movieReleasedState' is null/empty/missing for each 'movieTitle'
     */
    @Test
    @DisplayName("Test Movie ReleasedState is Null Or Missing Procedure 1")
    public void testMovieReleasedStateNullOrMissingProcedure1() throws FileNotFoundException {
        
        AtomicBoolean isMoveReleasedStateNullOrMissing = new AtomicBoolean(false);
        
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = jsonString(movie, TITLE);
                    
                    jsonStream(movie, MOVIE_RELEASE)
                            .forEach(movieRelease -> {
                                
                                String countryReleased = jsonString(movieRelease, COUNTRY_RELEASED);
                                
                                jsonStream(movieRelease, MOVIE_ITEM)
                                        .forEach(movieItem -> {
                                            
                                            boolean isMenuItemTitleMissing = isFieldUndefined(movieItem, MOVIE_RELEASED_STATE);
                                            
                                            if (!isMenuItemTitleMissing &&
                                                    isBlank(jsonString(movieItem, MOVIE_RELEASED_STATE))) {
                                                
                                                isMoveReleasedStateNullOrMissing.set(true);
                                                log(format("ERROR: movieReleasedState is null under countryReleased: %s for "
                                                                + "movieTitle: %s",
                                                        countryReleased,
                                                        movieTitle));
                                            }
                                            
                                            if (isMenuItemTitleMissing) {
                                                log(format("WARN: movieReleasedState field is missing under countryReleased: %s for "
                                                                + "movieTitle: %s",
                                                        countryReleased,
                                                        movieTitle));
                                            }
                                        });
                                
                                
                            });
                });
        
        if (isMoveReleasedStateNullOrMissing.get()) {
            fail();
        }
    }
    
    /**
     * Procedure 2 using try and catch block:
     * The Test case validates 'movieReleasedState' is null/missing for each 'movieTitle'
     */
    @Test
    @DisplayName("Test Movie ReleasedState is Null Or Missing Procedure 2")
    public void testMovieReleasedStateNullOrMissingProcedure2() throws FileNotFoundException {
        
        AtomicBoolean isMoveReleasedStateNullOrMissing = new AtomicBoolean(false);
        
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = jsonString(movie, TITLE);
                    
                    jsonStream(movie, MOVIE_RELEASE)
                            .forEach(movieRelease -> {
                                
                                String countryReleased = jsonString(movieRelease, COUNTRY_RELEASED);
                                
                                jsonStream(movieRelease, MOVIE_ITEM)
                                        .forEach(movieItem -> {
                                            
                                            try {
                                                
                                                String movieReleasedState = jsonString(movieItem, MOVIE_RELEASED_STATE);
                                                
                                                if (isBlank(movieReleasedState)) {
                                                    isMoveReleasedStateNullOrMissing.set(true);
                                                    log(format("ERROR: movieReleasedState is null/empty under countryReleased: %s for "
                                                                    + "movieTitle: %s",
                                                            countryReleased,
                                                            movieTitle));
                                                }
                                            } catch (Exception e) {
                                                log(format("WARN: movieReleasedState field is missing under countryReleased: %s for "
                                                                + "movieTitle: %s",
                                                        countryReleased,
                                                        movieTitle));
                                            }
                                            
                                        });
                                
                                
                            });
                });
        
        if (isMoveReleasedStateNullOrMissing.get()) {
            fail();
        }
    }
    
}
