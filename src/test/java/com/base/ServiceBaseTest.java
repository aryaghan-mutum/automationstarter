package com.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class ServiceBaseTest extends BaseTest {
    
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
        checkLogMessagesAndAddAttachment();
    }
    
}
