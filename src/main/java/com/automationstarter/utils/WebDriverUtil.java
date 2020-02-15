package com.automationstarter.utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.forward;
import static com.codeborne.selenide.Selenide.getFocusedElement;
import static com.codeborne.selenide.Selenide.title;
import static java.lang.String.format;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public class WebDriverUtil {
    
    /**
     * Refresh modify order pop up
     */
    public final WebDriverUtil customRefresh() {
        getFocusedElement().sendKeys(Keys.TAB);
        getFocusedElement().click();
        return this;
    }
    
    /**
     * Clicks a web element link using WebDriver Runner
     */
    public final static void clickWebElementLink(final String elementLink) {
        WebElement webElement = WebDriverRunner.getWebDriver().findElement(xpath(elementLink));
        executeJavaScript("arguments[0].click();", webElement);
    }
    
    /**
     * Get Selenium WebDriver
     */
    public final WebDriver getWebDriver() {
        return WebDriverRunner.getWebDriver();
    }
    
    /**
     * Get url of the current page
     *
     * @return
     */
    public final static String getCurrentUrl() {
        return WebDriverRunner.url();
    }
    
    /**
     * Get title of the current page
     */
    public final static String getTitle() {
        return title();
    }
    
    /**
     * Go back to the page
     */
    public final WebDriverUtil goBack() {
        back();
        return this;
    }
    
    public final WebDriverUtil goBack(final int maxTimesToGoBack) {
        IntStream.range(0, maxTimesToGoBack).forEach(back -> back());
        return this;
    }
    
    /**
     * Go forward to the page
     */
    public final WebDriverUtil goForward() {
        forward();
        return this;
    }
    
    /**
     * Maximize the window
     */
    public final WebDriverUtil maximize() {
        getWebDriver().manage().window().maximize(); //Configuration.startMaximized = true;
        return this;
    }
    
    /**
     * Switch any tab based on index
     */
    public final WebDriverUtil switchTab(final int index) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        ArrayList<String> tabsList = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabsList.get(index));
        return this;
    }
    
    /**
     * Switch to parent tab
     */
    public final WebDriverUtil switchToParentTab() {
        switchTab(0);
        return this;
    }
    
    /**
     * Switch to child tab
     */
    public final WebDriverUtil switchToChildTab() {
        switchTab(1);
        return this;
    }
    
    /**
     * Scroll bottom of the page using coordinates
     */
    public final WebDriverUtil scrollBottomByCoordinates(final int yCoordinate) {
        String scrollByCoordinates = format("window.scrollBy(%s,%s)", 0, yCoordinate);
        ((JavascriptExecutor) getWebDriver()).executeScript(scrollByCoordinates);
        return this;
    }
    
    /**
     * Scroll bottom of the page using coordinates
     */
    public final WebDriverUtil scrollUpByCoordinates(final int yCoordinate) {
        String scrollByCoordinates = format("window.scrollBy(%s,%s)", 0, -yCoordinate);
        ((JavascriptExecutor) getWebDriver()).executeScript(scrollByCoordinates);
        return this;
    }
    
    /**
     * Close tab based don index
     */
    public final WebDriverUtil closeTab(final int index) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        ArrayList<String> tabsList = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
        driver.switchTo().window(tabsList.get(index)).close();
        return this;
    }
    
    /**
     * Get the color code of an element
     */
    public String getElementColor(String cssSelector, String cssValue) {
        WebElement target = getWebDriver().findElement(cssSelector(cssSelector));
        String colorCode = target.getCssValue(cssValue);
        return Color.fromString(colorCode).asHex();
    }
    
}



