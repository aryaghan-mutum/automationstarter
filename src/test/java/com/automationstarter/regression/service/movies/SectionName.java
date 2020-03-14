package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import com.google.gson.JsonElement;
import io.qameta.allure.Step;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.automationstarter.constants.ServiceConstants.MOVIES;
import static com.automationstarter.constants.ServiceConstants.TITLE;
import static com.automationstarter.services.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static com.automationstarter.services.JsonService.isFieldUndefined;
import static com.automationstarter.services.JsonService.jsonStream;
import static com.automationstarter.services.JsonService.jsonString;
import static io.netty.util.internal.StringUtil.EMPTY_STRING;
import static org.jsoup.helper.StringUtil.isBlank;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Anurag Muthyam
 */

public class SectionName extends BaseTest {
    
    
    /**
     * Procedure 1: Test if the sectionName is null
     * If the sectionName == null, then the test FAILS
     * If the sectionName != null then the test PASSES
     * <p>
     * -> In Procedure 1, we are passing the json doc and a json field to getJsonStream()
     * -> Then we are applying flatMap() operator to go into the menuSections in the json file
     * -> And we are filtering to check the sectionName is null or not
     * -> findAny() helps to find sectionName = null
     * -> And finally returns the boolean value true or false by the terminal operator: isPresent();
     */
    @Test
    @DisplayName("Find Menu SectionName Is Null Procedure 1")
    public void findMenuSectionNameIsNullProcedure1() throws FileNotFoundException {
        
        boolean areAllActorsNullFound = jsonStream(retrieveMoviesServiceDoc(), "payload.movies")
                .flatMap(menuID -> jsonStream(menuID, "cast"))
                .filter(this::areAllActorsNull)
                .findAny()
                .isPresent();
        
        if (areAllActorsNullFound) {
            fail();
        }
    }
    
    /**
     * Procedure 2: Test if the sectionName is null
     * If the sectionName == null, then the test FAILS
     * If the sectionName != null then the test PASSES
     * <p>
     * -> In Procedure 2, we are passing the json doc and a json field to getJsonStream()
     * -> Then we are applying forEach() operator to iterate each and every menuSections
     * -> If menuSections = null then peek() operator prints out menuSections = null
     * -> Then we are using the terminal operator: count() to get total count of menuSections which are null
     */
    @Test
    @DisplayName("Find Menu SectionName Is Null Procedure 2")
    public void findMenuSectionNameIsNullProcedure2() throws FileNotFoundException {
        
        AtomicBoolean areActorsNull = new AtomicBoolean(false);
    
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(menuID -> {
                    
                    long actorsCount = jsonStream(menuID, "cast")
                            .filter(this::areAllActorsNull)
                            .peek(menuSection -> logInfo(String.format("All actors are null for title: %s", jsonString(menuID, TITLE))))
                            .count();
                    
                    if (actorsCount > 0) {
                        areActorsNull.set(true);
                    }
                });
        
        if (areActorsNull.get()) {
            fail();
        }
    }
    
    @Step("check all actors are null and return a boolean expression")
    private boolean areAllActorsNull(JsonElement menuSection) {
        return isFieldUndefined(menuSection, "actor1") ||
                jsonString(menuSection, "actor1") == null &&
                        isFieldUndefined(menuSection, "actor2") ||
                jsonString(menuSection, "actor2") == null &&
                        isFieldUndefined(menuSection, "actor3") ||
                jsonString(menuSection, "actor3") == null;
    }
    
    /**
     * The Test case validates the 'fileReference' is null/empty in dispatcher
     * -> If the 'media' field is missing, the test case logs for which venueCode it is missing and Fails
     * -> If the 'fileReference' is null/empty, the test case logs for which venueCode it is missing and Fails
     */
    @Test
    @DisplayName("Test FileReference Is Null Or Empty")
    public void testFileReferenceIsNullOrEmpty() throws FileNotFoundException {
        
        AtomicInteger invalidFileReferenceCount = new AtomicInteger();
        
        jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .forEach(venueArray -> {
                    
                    String venueCode = jsonString(venueArray, EMPTY_STRING);
                    
                    try {
                        jsonStream(venueArray, EMPTY_STRING)
                                .forEach(media -> {
                                    if (isFileReferenceNullInDispatcher(media)) {
                                        log(String.format("fileReference is null/empty for venueCode: {} in dispatcher", venueCode));
                                        invalidFileReferenceCount.incrementAndGet();
                                    }
                                });
                    } catch (Exception e) {
                        log("Exception: %s" + e);
                        log(String.format("media field is missing for venueCode: {} in dispatcher", venueCode));
                    }
                });
        
        if (invalidFileReferenceCount.get() > 0) {
            fail();
        }
    }
    
    @Step("Returns true if the 'fileReference' is null/empty, otherwise returns false.")
    private boolean isFileReferenceNullInDispatcher(JsonElement media) {
        return isFieldUndefined(media, EMPTY_STRING) ||
                isBlank(jsonString(media, EMPTY_STRING));
    }
}
