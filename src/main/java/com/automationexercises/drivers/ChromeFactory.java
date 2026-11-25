package com.automationexercises.drivers;

import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ChromeFactory extends AbsrtactDriver {


    private ChromeOptions getOptions() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-popup-infobars");
        options.addArguments("--disable-popup-extensions");
        options.addArguments("--disable-popup-gpu");
        options.addArguments("--start-maximized");
        Map<String,Object> prefs = new HashMap<>();
        String userDir = System.getProperty("user.dir");
        String downloadPath = userDir + "\\src\\test\\resources\\downloads";
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.default_directory" , downloadPath);
        options.setExperimentalOption("prefs" , prefs);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ENABLE_DOWNLOADS,true);
        options.addArguments("download.default_directory", downloadPath);
        options.setCapability(CapabilityType.ENABLE_DOWNLOADS,true);

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless") ||
        PropertyReader.getProperty("executionType").equalsIgnoreCase("RemoteHeadless")) {
            options.addArguments("--headless");
        }
        // Disable Chrome password leak detection
        options.setExperimentalOption(
                "prefs",
                java.util.Map.of(
                        "profile.password_manager_leak_detection", false
                )
        );

        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        return options;
    }

    @Override
    public WebDriver createDriver() {

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") ||
        PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")) {
            return new ChromeDriver(getOptions());
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
