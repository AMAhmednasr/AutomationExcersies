package com.automationexercises.utils.report;

import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.core.LoggerContext;

import javax.naming.Name;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static com.automationexercises.media.ScreenRecordManager.RECORDING_PATH;
import static com.automationexercises.utils.dataReader.PropertyReader.getProperty;

public class AllureAttachmentManager {

    // attach screenshot , attach logs , attach video

    public static void attachScreenshot(String name , String path){

        try {
            Path screenshot = Path.of(path);
            if (Files.exists(screenshot)) {
                Allure.addAttachment(name , Files.newInputStream(screenshot));
            } else {

                LogsManager.error("Screenshot file not found at path: " + path);
            }
        } catch (Exception e) {
            LogsManager.error("Failed to attach screenshot: " + e.getMessage());
        }
    }

    public static void attachLogs(){
        try {
            LogsManager.shutdown();
            File logFile = new File(LogsManager.LOGS_PATH + "logs.log");
            LogsManager.getContext(false).reconfigure();
            if (logFile.exists()) {
                Allure.addAttachment("logs.log" , Files.readString(logFile.toPath()));
            }
            } catch (Exception e) {
                LogsManager.error("Failed to attach logs: " + e.getMessage());
    }
}

public static void attachRecords(String testMethodName){
        if (getProperty("recordTests").equalsIgnoreCase("true")) {
            try {
                File record = new File(RECORDING_PATH + testMethodName + ".mp4");
                if (record != null && record.getName().endsWith(".mp4")) {
                    Allure.addAttachment(testMethodName , "video/mp4", Files.newInputStream(record.toPath()), ".mp4");
                } else {
                    LogsManager.error("Screen recording file not found for test method: " + testMethodName);
                }
            } catch (Exception e){
                LogsManager.error("Failed to attach screen recording: " + e.getMessage());
            }
        }
}

}
