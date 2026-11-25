package com.automationexercises.listeners;

import com.automationexercises.FileUtils;
import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.drivers.WebDriverProvider;
import com.automationexercises.media.ScreenRecordManager;
import com.automationexercises.media.ScreenshotsManager;
import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import com.automationexercises.utils.report.AllureAttachmentManager;
import com.automationexercises.utils.report.AllureConstant;
import com.automationexercises.utils.report.AllureEnvironmentManager;
import com.automationexercises.utils.report.AllureReportGenerator;
import com.automationexercises.validation.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

import static com.automationexercises.utils.report.AllureConstant.REPORT_PATH;
import static com.automationexercises.utils.report.AllureConstant.RESULTS_FOLDER;

public class TestNGListeners  implements IExecutionListener, IInvokedMethodListener, ITestListener {


    public void onExecutionStart() {
        LogsManager.info("Test execution started.");
        cleanTestOutputDirectories();
        LogsManager.info("Test output directories cleaned.");
        createTestOutputDirectories();
        LogsManager.info("Test output directories created.");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded.");
        AllureEnvironmentManager.setEnvronmentVariables();
        LogsManager.info("Allure environment variables set.");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.generateReports(false);
        LogsManager.info("Allure reports generated.");
        AllureReportGenerator.copyHistory();
        LogsManager.info("Allure history copied.");
        AllureReportGenerator.generateReports(true);
        LogsManager.info("Allure reports generated with history.");
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Test Execution finished and Allure report opened in browser.");
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

        if (method.isTestMethod()) {
            ScreenRecordManager.startRecording();
            LogsManager.info("Test Case" + testResult.getName() + " execution started and screen recording started.");
        }    }


    @Override
    public void afterInvocation(
            IInvokedMethod method,
            ITestResult testResult) {

        if (!method.isTestMethod()) {
            return;
        }

        WebDriver driver = GUIDriver.get();

        try {
            ScreenRecordManager.stopRecording(testResult.getName());

            Validation.assertAll(testResult);

            // Get driver from the same ThreadLocal used by GUIDriver
            driver = GUIDriver.get();

            if (driver != null) {
                switch (testResult.getStatus()) {

                    case ITestResult.SUCCESS ->
                            ScreenshotsManager.takeFullPageScreenshot(
                                    driver, "passed-" + testResult.getName());

                    case ITestResult.FAILURE ->
                            ScreenshotsManager.takeFullPageScreenshot(
                                    driver, "failed-" + testResult.getName());

                    case ITestResult.SKIP ->
                            ScreenshotsManager.takeFullPageScreenshot(
                                    driver, "skipped-" + testResult.getName());

                    default -> LogsManager.info(
                            "No screenshot required for test status: "
                                    + testResult.getStatus());
                }
            } else {
                LogsManager.warn(
                        "Driver is null. Screenshot skipped for: "
                                + testResult.getName());
            }

            AllureAttachmentManager.attachLogs();
            AllureAttachmentManager.attachRecords(testResult.getName());

        } catch (Exception e) {
            LogsManager.error(
                    "Error in afterInvocation: " + e.getMessage());
        }
    }


    public void onTestSuccess(ITestResult result) {

        LogsManager.info("Test Case " + result.getName() + " Passed and screen recording stopped.");

    }


    public void onTestFailure(ITestResult result) {
LogsManager.info("Test Case " + result.getName() + "Failed and screen Recording stopped.");
        if (GUIDriver.get() != null) {
            // ضع هنا كود أخذ لقطة الشاشة أو تسجيل الخطأ
            LogsManager.error("Test failed: " + result.getName());
        } else {
            LogsManager.warn("Driver is null, skipping failure actions for: " + result.getName());
        }

    }


    public void onTestSkipped(ITestResult result) {

        LogsManager.info("Test Case " + result.getName() + " Skipped and screen recording stopped.");
    }

    // cleaning and creating dirs (Logs, screenshots , recordings , Allure-Results)

    public void cleanTestOutputDirectories(){
        FileUtils.cleanDirectory(RESULTS_FOLDER.toFile());
        LogsManager.info("Allure results folded deleted sucessfuly" + RESULTS_FOLDER);
        FileUtils.cleanDirectory(new File(ScreenshotsManager.SCREEMSHOTS_PATH));
        FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDING_PATH));
        FileUtils.cleanDirectory(new File("src/test/resources/downloads/"));
        FileUtils.forceDelete(new File(LogsManager.LOGS_PATH + File.separator + "Logs.log"));
    }

    public void createTestOutputDirectories(){
        FileUtils.createDirectory(ScreenshotsManager.SCREEMSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManager.RECORDING_PATH);
        FileUtils.createDirectory("src/test/resources/downloads/");


        //   FileUtils.createDirectory(REPORT_PATH.toString());


    }
}
