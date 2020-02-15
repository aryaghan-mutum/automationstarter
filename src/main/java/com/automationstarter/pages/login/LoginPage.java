package com.automationstarter.pages.login;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPage {
    
    @Step("Logs into HomePort Web App Admin Page")
    public LoginPage loginAdminPage() {
        
        return this;
    }
}
