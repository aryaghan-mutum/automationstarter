package com.base;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static java.lang.String.format;

public abstract class UIBaseTest extends BaseTest {
    
    //public LoginPage loginPage;
    private WebDriver driver;
    
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        
        System.setProperty("webdriver.chrome.driver", "chromedriverpath/chromedriver.exe");
        
        try {
            driver = new ChromeDriver();
            String appUrl = "http://www.facebook.com/";
            Configuration.timeout = 10000;
            getWebDriver().manage().window().maximize();
        } catch (Exception ex) {
            log(format("Exception while instantiating driver. ", ex));
        }
        
        sleep(2000);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
        if (driver != null) {
            driver.quit();
        }
        checkLogMessagesAndAddAttachment();
        driver.close();
    }
    
}
