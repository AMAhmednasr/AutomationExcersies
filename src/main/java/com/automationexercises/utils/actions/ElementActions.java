package com.automationexercises.utils.actions;

import com.automationexercises.utils.WaitManager;
import com.automationexercises.utils.logs.LogsManager;
import groovy.util.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementActions {

    private final WebDriver driver;
    private final WaitManager waitManager;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    // Click
    public ElementActions click(By locator) {

        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);

                if (element.isDisplayed() && element.isEnabled()) {

                    new Actions(d)
                            .moveToElement(element)
                            .perform();

                    element.click();

                    LogsManager.info("Clicked on element: " + locator);
                    return true;
                }

                return false;

            } catch (Exception e) {
                return false;
            }
        });

        return this;
    }


    // Hover
    public ElementActions hover(By locator) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);

                new Actions(d).moveToElement(element).perform();

                LogsManager.info("Hovered over element: " + locator);
                return true;

            } catch (Exception e) {

                LogsManager.error("Error hovering over element:" + locator , e.getMessage());
                return false;
            }
        });
        return this;
    }


    // Type
    public ElementActions type(By locator, String text) {

       waitManager.fluentWait().until(d ->
       {
           try {
               WebElement element = d.findElement(locator);
               scrollToElementJS(locator);
               element.clear();
               element.sendKeys(text);
               LogsManager.info("Typed text '" + text + "' into element: " + locator);
               return true;
           } catch (Exception e) {
               return false;
           }
       }
       );

        return this;
    }

    // Get Text
    public String getText(By locator) {

       return waitManager.fluentWait().until(d ->
               {
               try{
                   WebElement element = d.findElement(locator);
                   scrollToElementJS(locator);
                   String msg = element.getText();
                   LogsManager.info("Text from element " + locator + ": " + msg);
                   return !msg.isEmpty() ? msg : null;
               } catch (Exception e) {
                   return null;
               }

               }

       ); }




    // Upload File
    public ElementActions uploadFile(By locator, String filePath) {
      String fileAbsolutePath = System.getProperty("user.dir") + File.separator + filePath;
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolutePath);
                        LogsManager.info("Uploaded file '" + fileAbsolutePath + "' into element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }


    // find element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }


    // function to scroll to an element using js
    public ElementActions scrollToElementJS(By locator) {

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("""
                        arguments[0].scrollIntoView({Behavior:"auto",block:"center",inline:"center"});""", findElement(locator));

        return this;
    }

    // Select from dropDown
    public ElementActions selectFromDropdown(By locator , String value){

        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        Select select = new Select(element);
                        select.selectByVisibleText(value);
                        LogsManager.info("Selected value: " + value + " From dropdown " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );

        return this;
    }




}