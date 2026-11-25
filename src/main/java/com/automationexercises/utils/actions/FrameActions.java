package com.automationexercises.utils.actions;

import com.automationexercises.utils.WaitManager;
import com.automationexercises.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {

    private final WebDriver driver;
    private final WaitManager waitManager;

    public FrameActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    /**
     * Switch to frame by index
     *
     * @param index frame index
     */
    public void switchToFrameByIndex(int index) {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(index);
                LogsManager.info("Switched to frame by index: " + index);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * Switch to frame by name or id
     *
     * @param nameOrId frame name or id
     */
    public void switchToFrameByNameOrId(String nameOrId) {

        // lamda avail wait and take action if frame is available and switch to it
        // Stale element exception is handled by fluent wait and retrying the action until timeout
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(nameOrId);
                LogsManager.info("Switched to frame by name or id: " + nameOrId);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * Switch to frame by WebElement
     *
     * @param frameLocator frame locator
     */
    public void switchToFrameByElement(By frameLocator) {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(d.findElement(frameLocator));
                LogsManager.info("Switched to frame by element: " + frameLocator);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * Switch back to default content
     */
    public void switchToDefaultContent() {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().defaultContent();
                LogsManager.info("Switched back to default content");
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
