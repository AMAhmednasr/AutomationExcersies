package com.automationexercises.media;

import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.logs.LogsManager;
import com.automationexercises.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotsManager {

    public static final String SCREEMSHOTS_PATH = "/test-output/screenshots/";


    static void main() {
        LogsManager.info(SCREEMSHOTS_PATH);
    }
    // Take full page screenshot
    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Capture screenshot using TakesScreenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotDestination = new File(SCREEMSHOTS_PATH + screenshotName + "-" + TimeManager.getTimeStamp() + ".png");
            FileUtils.copyFile(screenshotFile, screenshotDestination);


            AllureAttachmentManager.attachScreenshot(screenshotName , screenshotDestination.getAbsolutePath());

            LogsManager.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Capturing Screenshot Failed: " + e.getMessage());
        }
    }

    // Take screenshot of a specific element
    public static void takeElementScreenshot(WebDriver driver, By elementSelector) {
        try {
            // Highlight the element if needed
            // Capture screenshot using TakesScreenshot
            String ariaName = driver.findElement(elementSelector).getAccessibleName();
            File screenshotFile = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotSrc = new File(SCREEMSHOTS_PATH + ariaName + "-" + TimeManager.getTimeStamp() + ".png");
            FileUtils.copyFile(screenshotFile, screenshotSrc);

            // todo: Attach screenshot to Allure report if needed

            LogsManager.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Failed to capture element screenshot: " + e.getMessage());
        } 
    }
}