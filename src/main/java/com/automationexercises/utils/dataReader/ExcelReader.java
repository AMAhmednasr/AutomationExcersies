package com.automationexercises.utils.dataReader;

import com.automationexercises.utils.logs.LogsManager;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;

public class ExcelReader {



    public static String getExcelData(
            String excelFileName,
            String sheetName,
            int rowNum,
            int colNum) {

        try (FileInputStream file = new FileInputStream(excelFileName);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum - 1);

            DataFormatter formatter = new DataFormatter();

            return formatter.formatCellValue(
                    row.getCell(colNum - 1)
            );

        } catch (Exception e) {
            LogsManager.error("Failed to read Excel file: " , excelFileName , e.getMessage());
            return null;
        }
    }
}
