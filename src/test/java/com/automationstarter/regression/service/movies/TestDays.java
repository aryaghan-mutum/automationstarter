package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.automationstarter.constants.ServiceConstants.DAYS;
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

public class TestDays extends BaseTest {
    
    /**
     * The Test case checks if each 'day' is null empty or missing to its associated 'menuTitle'
     */
    @Test
    @DisplayName("Test Day Is Null Or Empty Or Missing For Movies")
    public void testDayIsNullOrEmptyOrMissingForMovies() throws FileNotFoundException {
        
        AtomicInteger invalidDayCount = new AtomicInteger();
        
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = jsonString(movie, TITLE);
                    boolean isDayFieldMissing = isFieldUndefined(movie, DAYS);
                    
                    try {
                        if (!isDayFieldMissing && jsonStream(movie, DAYS).count() == 0) {
                            invalidDayCount.incrementAndGet();
                            log(format("ERROR: day is empty for movieTitle: %s", movieTitle));
                        } else if (isDayFieldMissing) {
                            log(format("WARN: day field is missing for movieTitle: %s", movieTitle));
                        }
                    } catch (Exception e) {
                        invalidDayCount.incrementAndGet();
                        log(format("ERROR: day is null for movieTitle: %s", movieTitle));
                        log(format("Exception: %s", e));
                    }
                });
        
        if (invalidDayCount.get() > 0) {
            fail();
        }
    }
}
