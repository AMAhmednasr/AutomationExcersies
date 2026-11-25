package com.automationexercises.utils.report;

import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import org.openqa.selenium.logging.Logs;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AllureConstant {

    static void main() {
        LogsManager.info(USER_HOME.toString());
    }

    // Paths - final - static

    public static final Path USER_DIR = Paths.get(PropertyReader.getProperty("user.dir"));
    public static final Path USER_HOME = Paths.get(PropertyReader.getProperty("user.home"));

    public static final Path RESULTS_FOLDER = Paths.get(String.valueOf(USER_DIR), "test-output", "allure-results" , File.separator);
    public static final Path REPORT_PATH = Paths.get(String.valueOf(USER_DIR), "test-output", "reports" , File.separator); // Single report .html
    public static final Path FULL_REPORT_PATH = Paths.get(String.valueOf(USER_DIR), "test-output","full-report" , File.separator);

    public static final Path HISTORY_FOLDER = Paths.get(FULL_REPORT_PATH.toString(), "history" , File.separator);
    public static final Path RESULT_HISTORY_FOLDER = Paths.get(RESULTS_FOLDER.toString(), "history" , File.separator); // Copy generated history to it


    public static final String INDEX_HTML = "index.html";
    public static final String REPORT_PREFEX = "AllureReport_"; // Timestamp
    public static final String REPORT_EXTENSION = ".html";

    public static final String ALLURE_ZIP_BASE_URL = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/";

    public static final Path EXTRACTION_DIR = Paths.get(String.valueOf(USER_HOME), ".m2/repository/allure", File.separator);
}
