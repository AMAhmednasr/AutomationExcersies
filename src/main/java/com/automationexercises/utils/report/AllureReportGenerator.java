package com.automationexercises.utils.report;

import com.automationexercises.FileUtils;
import com.automationexercises.utils.TerminalUtils;
import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.logs.LogsManager;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTRotY;
import org.testng.util.TimeUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.automationexercises.utils.dataReader.PropertyReader.getProperty;
import static com.automationexercises.utils.report.AllureConstant.HISTORY_FOLDER;
import static com.automationexercises.utils.report.AllureConstant.RESULT_HISTORY_FOLDER;

public class AllureReportGenerator {

    // Generate Allure Report

    // --single-file: Generate a single HTML file for the report.
    public static void generateReports(boolean isSingleFile){
        Path outputFolder = isSingleFile ? AllureConstant.REPORT_PATH : AllureConstant.FULL_REPORT_PATH;
        // allure generate -o reports --single-file --clean
        List<String>  command = new ArrayList<>(List.of(
                AllureBinaryManager.getExcutable().toString(),
                "generate",
                AllureConstant.RESULTS_FOLDER.toString(),
                "-o",
                outputFolder.toString(),
                "--clean"
        ));

        if (isSingleFile) command.add("--single-file");
        TerminalUtils.excuteTerminalCommand(command.toArray(new String[0]));

    }


    // Open Allure Report in Browser

    public static void openReport(String reportFileName){
        if (!getProperty("openAllureReportAfterExecution").equalsIgnoreCase("true")) return;

        Path reportPath = AllureConstant.REPORT_PATH.resolve(reportFileName);
        switch (FileUtils.OSUtils.getCurrentOS()){
            case WINDOWS -> TerminalUtils.excuteTerminalCommand("cmd.exe", "/c", "start", reportPath.toString());
            case MAC , LINUX -> TerminalUtils.excuteTerminalCommand("open", reportPath.toString());

            default -> LogsManager.warn("Unsupported OS for opening the report: " + FileUtils.OSUtils.getCurrentOS());
        }
    }

    // copy history folder to results folder
    public static void copyHistory(){
    try {
        FileUtils.copyDirectory(HISTORY_FOLDER.toFile(), RESULT_HISTORY_FOLDER.toFile());
    } catch (Exception e) {
        LogsManager.error("Failed to copy history folder: " + e.getMessage());
    }
}

// rename report file to AllureReport.html
public static String renameReport(){
        String newFileName = AllureConstant.REPORT_PREFEX + TimeManager.getTimeStamp() + AllureConstant.REPORT_EXTENSION; // AllureReport_2026-11-07_14-30-00.html
        FileUtils.renameFile(AllureConstant.REPORT_PATH.resolve(AllureConstant.INDEX_HTML).toString(), newFileName);
        return newFileName;
    }

}
