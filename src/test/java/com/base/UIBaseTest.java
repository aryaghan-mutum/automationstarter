package com.base;

import com.automationstarter.pages.login.LoginPage;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class UIBaseTest extends BaseTest {
    
    public LoginPage loginPage;
    
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        
//        Session session = startDefaultSession();
//        Configuration.timeout = 10000;
//        getWebDriver().manage().window().maximize();
//        loginPage = new LoginPage();
//        loginPage.loginHomePort();
        sleep(2000);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
        checkLogMessagesAndAddAttachment();
    }
    
}
