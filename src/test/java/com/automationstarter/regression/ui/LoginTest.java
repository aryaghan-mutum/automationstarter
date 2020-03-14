package com.automationstarter.regression.ui;

import com.base.UIBaseTest;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest extends UIBaseTest {
    
    @Test
    public void testCase() {
    
        
        $(By.name("q")).val("selenide").pressEnter();
        $(By.className("rc")).shouldBe(Condition.visible);
        int size = $$(By.className("rc")).size();
    
        System.out.println("------");
        boolean b = true;
        
    }
}
