package com.automationexercises.drivers;

import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class FirefoxFactory extends AbsrtactDriver {

    private FirefoxOptions getOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-popup-infobars");
        options.addArguments("--disable-popup-extensions");
        options.addArguments("--disable-popup-gpu");
        options.addArguments("--start-maximized");
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("RemoteHeadless")) {
            options.addArguments("--headless");
        }
        return options;
    }

    @Override
    public WebDriver createDriver() {  if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") ||
            PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")) {
        return new FirefoxDriver(getOptions());
    }

    else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote") ||
            PropertyReader.getProperty("executionType").equalsIgnoreCase("RemoteHeadless")) {
        try {
            return new RemoteWebDriver(
                    new URI("http://" + remotehost + ":" + remoteport + "/wd/hub").toURL(), getOptions()
            );
        } catch (Exception e) {
            LogsManager.error("Failed to create remote WebDriver: " + e.getMessage());
            throw new RuntimeException("Failed to create remote WebDriver", e);
        }
    }
    else {
        LogsManager.error("Invalid execution type: " + PropertyReader.getProperty("executionType"));
        throw new IllegalArgumentException("Invalid execution type: " + PropertyReader.getProperty("executionType"));
    }
    }
    }

