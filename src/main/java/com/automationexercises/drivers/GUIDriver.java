package com.automationexercises.drivers;


import com.automationexercises.utils.actions.AlertActions;
import com.automationexercises.utils.actions.BrowserActions;
import com.automationexercises.utils.actions.ElementActions;
import com.automationexercises.utils.actions.FrameActions;
import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import com.automationexercises.validation.Validation;
import com.automationexercises.validation.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {

    private static String browser = PropertyReader.getProperty("browserType");

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public GUIDriver() {
        LogsManager.info("Inializing GUIDriver with browser: " + browser);
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for browser: " + browserType);
         AbsrtactDriver absrtactDriver = browserType.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(( absrtactDriver.createDriver()));
        driverThreadLocal.set(driver);
    }


    public ElementActions element(){
        return new ElementActions(get());
    }

    public BrowserActions browser(){
        return new BrowserActions(get());
    }

    public FrameActions frame(){
        return new FrameActions(get());
    }

    public AlertActions alert(){
        return new AlertActions(get());
    }

    // Soft Assertions
    public Validation validation(){
        return new Validation(get());
    }

    // Hard Assertions
    public Verification verification(){
        return new Verification(get());
    }



    public static WebDriver get() {

        return driverThreadLocal.get();
    }

    public static WebDriver quitDriver() {
        driverThreadLocal.get().quit();

        /*  if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }*/
        return null;
    }
}