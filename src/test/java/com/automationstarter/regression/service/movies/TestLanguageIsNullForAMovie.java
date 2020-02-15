package com.automationstarter.regression.service.movies;

import com.base.BaseTest;
import com.google.gson.JsonElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static com.automationstarter.constants.ServiceConstants.LANGUAGE;
import static com.automationstarter.constants.ServiceConstants.MOVIES;
import static com.automationstarter.constants.ServiceConstants.TITLE;
import static com.automationstarter.services.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static com.automationstarter.services.JsonService.isFieldUndefined;
import static com.automationstarter.services.JsonService.jsonStream;
import static com.automationstarter.services.JsonService.jsonString;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Anurag Muthyam
 */

/**
 * Test if the language is null
 * If the language == null, then the test FAILS
 * If the language != null then the test PASSES
 */

public class TestLanguageIsNullForAMovie extends BaseTest {
    
    /**
     * Approach 1 using filter(), peek(), findAny() and isPresent():
     * The Test case returns a boolean value. if the value is true then FAILS
     */
    @Test
    @DisplayName("Find language is null Procedure 1")
    public void findLanguageIsNullProcedure1() throws FileNotFoundException {
        
        boolean isLanguageNullFound = jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .filter(this::isLanguageNullForProcedure1)
                .peek(movie -> log(String.format("language is null for title: %s", jsonString(movie, TITLE))))
                .findAny()
                .isPresent();
        
        if (isLanguageNullFound) {
            fail();
        }
    }
    
    @Step("check language is null for procedure 1")
    private boolean isLanguageNullForProcedure1(JsonElement movie) {
        try {
            return jsonString(movie, LANGUAGE) == null;
        } catch (Exception ex) {
            log("Exception: %s" + ex.getMessage());
            log("jsonString method throws exception when the tag is not present, make sure that is also treated as null");
            return true;
        }
    }
    
    /**
     * Approach 2 using filter(), peek(), and count():
     * The Test case returns a total count. if the count > 0 then FAILS
     */
    @Test
    @DisplayName("Find language is null Procedure 2")
    public void findLanguageIsNullProcedure2() throws FileNotFoundException {
        
        long languageCount = jsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .filter(this::isLanguageNullForProcedure2)
                .peek(movie -> log(String.format("language is null for title: %s", jsonString(movie, TITLE))))
                .count();
        
        if (languageCount > 0) {
            fail();
        }
    }
    
    @Step("check language is null for procedure 2")
    private boolean isLanguageNullForProcedure2(JsonElement movie) {
        return isFieldUndefined(movie, LANGUAGE) || jsonString(movie, LANGUAGE) == null;
    }
    
}
