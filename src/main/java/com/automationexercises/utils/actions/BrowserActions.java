package com.automationexercises.utils.actions;

import com.automationexercises.utils.WaitManager;
import com.automationexercises.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {

    private final WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Maximize window
     */

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    // get current url
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        LogsManager.info("Current url: " + url);
        return url;
    }

    // get page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    // navigate to url
    public void navigateTo(String url) {
        driver.get(url);
        LogsManager.info("Navigated to: " + url);

    }

    // refresh page
    public void refreshPage() {
        driver.navigate().refresh();
    }

    // go back
    public void goBack() {
        driver.navigate().back();
    }

    // go forward
    public void goForward() {
        driver.navigate().forward();
    }

    // close current window
    public void closeAds() {
        String currentWindowHandle = driver.getWindowHandle();
        new WaitManager(driver).fluentWait().until(d->

        {
           return d.getWindowHandles().size() > 1;
        });
        for (String windowHandle : driver.getWindowHandles())
        {
            if (!windowHandle.equals(currentWindowHandle))
            driver.switchTo().window(windowHandle).close();
        }
        driver.close();
    }

    // Open new window
    public void openNewWindow() {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    // Open new tab
    public void openNewTab() {
        driver.switchTo().newWindow(WindowType.TAB); 

    }
}
