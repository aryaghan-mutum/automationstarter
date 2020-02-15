package com.automationstarter.regression.ui;

import com.automationstarter.pages.login.LoginPage;
import com.base.UIBaseTest;
import org.junit.jupiter.api.Test;

public class LoginTest extends UIBaseTest {
    
    private StringBuffer verificationErrors = new StringBuffer();
    
    @Test
    public void f() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginAdminPage();
    }
}
