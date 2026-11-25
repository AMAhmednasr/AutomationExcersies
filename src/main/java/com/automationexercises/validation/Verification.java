package com.automationexercises.validation;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Verification extends BaseAssertion {


    public Verification() {
        super();
    }
    public Verification(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    // Hard assertion - if fails, test stops execution

}
