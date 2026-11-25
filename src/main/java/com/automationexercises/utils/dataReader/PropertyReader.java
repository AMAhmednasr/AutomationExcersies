package com.automationexercises.utils.dataReader;

import com.automationexercises.utils.logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {

    private static final Properties properties = new Properties();

    // بلوك ساكن لتحميل الملفات تلقائياً أول ما يتم استخدام الكلاس
    static {
        loadProperties();
    }

    public static void loadProperties() {
        try {
            File resourceDir = new File("src/main/resources");
            if (resourceDir.exists()) {
                Collection<File> propertiesFiles = FileUtils.listFiles(resourceDir, new String[]{"properties"}, true);
                for (File file : propertiesFiles) {
                    try {
                        properties.load(FileUtils.openInputStream(file));
                    } catch (Exception e) {
                        LogsManager.error("Error loading properties file: ", file.getName(), e.getMessage());
                    }
                }
            }
            // دمج خصائص النظام إن وجد
            properties.putAll(System.getProperties());
        } catch (Exception e) {
            LogsManager.error("Error loading properties files: ", e.getMessage());
        }
    }

    public static String getProperty(String key) {
        try {
            // البحث أولاً في الملفات المحملة، ثم في System properties كاحتياط
            String value = properties.getProperty(key);
            if (value == null) {
                value = System.getProperty(key);
            }
            return value != null ? value.trim() : "";
        } catch (Exception e) {
            LogsManager.error("Error getting property for key: ", key, e.getMessage());
            return "";
        }
    }
}