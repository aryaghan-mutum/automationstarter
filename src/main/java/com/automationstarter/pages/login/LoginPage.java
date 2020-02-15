package com.automationstarter.pages.login;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.id;

public class LoginPage  {
    
    private final String usernameTextBox = "user_login";
    private final String passwordTextBox = "user_pass";
    private final String loginBtn = "wp-submit";
    private final String homePortVisitSiteBtn = "//*[@id='wp-admin-bar-site-name']/a";
    
    @Step("Logs into HomePort Web App Admin Page")
    public LoginPage loginAdminPage() {
     //   final HashMap<String, String> customPropertiesMap = getSession().getSessionConfiguration().getCustomProperties();
   //     $(id(usernameTextBox)).shouldBe(Condition.visible).sendKeys(customPropertiesMap.get("homePortUsername"));
    //    $(id(passwordTextBox)).shouldBe(Condition.visible).sendKeys(customPropertiesMap.get("homePortPassword"));
   //     $(id(loginBtn)).shouldBe(Condition.visible).click();
        return this;
    }
}
