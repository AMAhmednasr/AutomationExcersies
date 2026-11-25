package com.automationexercises.validation;

import com.automationexercises.FileUtils;
import com.automationexercises.utils.WaitManager;
import com.automationexercises.utils.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public abstract class BaseAssertion {

    protected  WebDriver driver;
    protected  WaitManager waitManager;
    protected ElementActions elementActions;
    protected BaseAssertion(){

    }

    protected BaseAssertion(WebDriver driver) {

        this.driver = driver;
        this.waitManager = new WaitManager(driver);
        this.elementActions = new ElementActions(driver);
    }

    protected abstract void assertTrue(boolean condition, String message);

    protected abstract void assertFalse(boolean condition, String message);

    protected abstract void assertEquals(Object actual, Object expected, String message);


    public BaseAssertion Equals(String actual, String expected, String message) {

                assertEquals(actual, expected, message);
                return this;
    }

    public void isElementVisible(By locator) {
        // check if element is visible, if not, wait for it to be visible

       boolean flag = waitManager.fluentWait().until(driver1->
        {
          try {
              driver1.findElement(locator).isDisplayed();
              return true;
          }
            catch (Exception e) {
                return false;
            }
        });

        assertTrue(flag, "Element is not visible: " + locator.toString());
    }

    // Verify page url
    public void assertPageUrl(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl, "Page URL does not match. Expected: " + expectedUrl + ", Actual: " + actualUrl);

    }

    // Verify page title
    public void assertPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Page title does not match. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }


    public void assertFileExists(String fileName, String message) {

        boolean fileExists = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .until(d -> FileUtils.isFileExists(fileName));

        assertTrue(fileExists, message);
    }
}
