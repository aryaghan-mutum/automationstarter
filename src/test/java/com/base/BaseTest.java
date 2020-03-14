package com.base;

import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.Allure.addAttachment;

public abstract class BaseTest  {
    
    @Getter
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static List<String> logMessages = new ArrayList<>();
    
    
    @BeforeEach
    public void setUp() throws Exception {
        logInfo("Setting up test");
    
        logInfo("Setting up test finished");
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        logInfo("Tearing down test");
        logInfo("Tearing down test finished");
    }
    
    public void log(String message){
        logger.info(message);
    }
    public void logInfo(String message){
        logger.info(message);
    }
    
    public void logError(String message) {
        logger.error(message);
    }
    
    public void checkLogMessagesAndAddAttachment() {
        if (logMessages != null && logMessages.size() > 0) {
            StringBuilder message = new StringBuilder();
            logMessages.forEach(s -> message.append(s).append("\n"));
            addAttachment("log", message.toString());
        }
    }
    
    private String parseErrorForAllure(Throwable e) {
        String stackTrace = ExceptionUtils.getStackTrace(e);
        return String.format("Error: %s\nStack Trace:\n%s\n", e.getMessage(), stackTrace);
    }
    
    protected SoftAssertions getSoftAssertions() {
        StringBuilder errorsCollected = new StringBuilder();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.errorsCollected().forEach(error -> errorsCollected.append(parseErrorForAllure(error)));
        return softAssertions;
    }
}
