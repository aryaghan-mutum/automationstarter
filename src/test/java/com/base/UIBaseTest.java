package com.base;

import com.automationstarter.utils.WebDriverUtil;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.String.format;

public abstract class UIBaseTest extends BaseTest {
    
    public WebDriver driver;
    private WebDriverUtil webDriverUtil;
    private String browser = "firefox";
    
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        
        driver = getWebDriver();
        try {
            open("https://www.google.com");
            Configuration.timeout = 10000;
            driver.manage().window().maximize();
        } catch (Exception ex) {
            logInfo(format("Exception while instantiating driver. ", ex));
        }
    }
    
    private WebDriver getWebDriver() {
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "drivers/msedgedriver");
            driver = new FirefoxDriver();
        }
        return driver;
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
