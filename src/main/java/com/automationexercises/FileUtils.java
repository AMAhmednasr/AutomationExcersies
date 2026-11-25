
package com.automationexercises;

import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    private static final String USER_DIR =
            System.getProperty("user.dir") + File.separator;

    private FileUtils() {
        // Private constructor to prevent instantiation
    }

    // Renaming File
    public static void renameFile(String oldName, String newName) {

        try {
            Path source = new File(oldName).toPath();

            if (!Files.exists(source)) {
                LogsManager.error("Source file does not exist: " + oldName);
                return;
            }

            Path destination = source.resolveSibling(newName);

            if (!source.equals(destination)) {

                Files.move(
                        source,
                        destination,
                        StandardCopyOption.REPLACE_EXISTING
                );

                LogsManager.info(
                        "Target File Path: " + oldName,
                        "File was renamed to " + newName
                );

            } else {
                LogsManager.info(
                        "Target File Path: " + oldName,
                        "Already has desired name " + newName
                );
            }

        } catch (IOException e) {
            LogsManager.error("Error renaming file: " + e.getMessage());
        }
    }

    // Creating Directory
    public static void createDirectory(String path) {

        try {
            File file = new File(USER_DIR + path);

            if (!file.exists()) {
                Files.createDirectories(file.toPath());
                LogsManager.info("Directory created: " + path);
            }

        } catch (IOException e) {
            LogsManager.error(
                    "Error creating directory: " + path + " - " + e.getMessage()
            );
        }
    }

    // Force Delete
    public static void forceDelete(File file){
        try {
            org.apache.commons.io.FileUtils.forceDelete(file);
            LogsManager.info("File deleted: " + file.getAbsolutePath());
        }catch (Exception e){
            LogsManager.error("Failed to delete file: " + file.getAbsolutePath(), e.getMessage());
        }
    }


    // Cleaning Directory
    public static void cleanDirectory(File file) {

        try {
            org.apache.commons.io.FileUtils.deleteQuietly(file); // if file in use -> skip

        } catch (Exception e) {
            LogsManager.error(
                    "Error cleaning directory: " + file.getAbsolutePath()
                            + " - " + e.getMessage()
            );
        }
    }

    // Copying Directory

    public static void copyDirectory(File sourcePath, File destinationPath) {
        try {
            File srcDir = sourcePath.isAbsolute()
                    ? sourcePath
                    : new File(USER_DIR, sourcePath.getPath());

            File destDir = destinationPath.isAbsolute()
                    ? destinationPath
                    : new File(USER_DIR, destinationPath.getPath());

            if (srcDir.exists() && srcDir.isDirectory()) {

                org.apache.commons.io.FileUtils.copyDirectory(
                        srcDir, destDir
                );

                LogsManager.info(
                        "Directory copied successfully from "
                                + srcDir + " to " + destDir
                );

            } else {
                LogsManager.warn(
                        "History directory does not exist: " + srcDir
                );
            }

        } catch (Exception e) {
            LogsManager.error(
                    "Error copying directory: " + e.getMessage()
            );
        }
    }

    public static class OSUtils {

        public enum OS {
            WINDOWS, MAC, LINUX, OTHER
        }

        public static OS getCurrentOS() {

            String os = PropertyReader.getProperty("os.name").toLowerCase();

            if (os.contains("win")) return OS.WINDOWS;
            else if (os.contains("mac")) return OS.MAC;
            else if (os.contains("nix") || os.contains("nux")
                    || os.contains("aix")) return OS.LINUX;
            else return OS.OTHER;
        }
    }

    //Check if the file exists
    public static boolean isFileExists(String fileName) {

        String downloadPath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources"
                + File.separator + "downloads";

        File file = new File(downloadPath, fileName);

        LogsManager.info("Download Directory: " + downloadPath);
        LogsManager.info("File Name: " + fileName);
        LogsManager.info("Absolute Path: " + file.getAbsolutePath());
        LogsManager.info("File Exists: " + file.exists());

        return file.exists() && file.isFile();
    }
}
