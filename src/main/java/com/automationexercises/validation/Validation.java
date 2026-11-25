package com.automationexercises.validation;

import com.automationexercises.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;


// Soft assertion - if fails, test continues execution and logs the failure
public class Validation  extends BaseAssertion {

    public Validation() {
        super();
    }
    public Validation(WebDriver driver) {
        super(driver);
    }

    private static boolean used = false; // Flag to track if soft assertion is used
    private static  SoftAssert softAssert = new SoftAssert();

    @Override
    protected void assertTrue(boolean condition, String message) {
        used = true; // Mark that soft assertion is used
        softAssert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        used = true; // Mark that soft assertion is used
        softAssert.assertFalse(condition, message);

    }

    @Override
    protected void assertEquals(Object actual, Object expected, String message) {
        used = true; // Mark that soft assertion is used
        softAssert.assertEquals(actual, expected, message);
    }

    public static void assertAll(ITestResult result) {

        if (!used) return;
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            LogsManager.error("Soft assertion failed: " + e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        }
        finally { // works when test fails or passes, so that softAssert is reset for the next test
                softAssert = new SoftAssert(); // Reset softAssert for the next test
        }

    }

}



