package com.automationexercises.tests;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.drivers.WebDriverProvider;
import com.automationexercises.listeners.TestNGListeners;
import com.automationexercises.utils.dataReader.JsonReader;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;


@Listeners(TestNGListeners.class)
public class BaseTest  implements WebDriverProvider {
    protected GUIDriver driver;
    protected JsonReader testData;

    @BeforeClass
    public void preCondition(){

        testData = new JsonReader("register-data");
    }


    @Override
    public WebDriver getWebDriver() {
        return driver.quitDriver();
    }
}
