package com.automationexercises.utils;

public class TimeManager {

// screenshots - logs - reports.
    public static String getTimeStamp() {
        return  new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
    }

    // for unique test data like email, username, etc.

    public static String getSimpleTimestamp() {
        // return a timestamp in numbers only format, like 1681234567890
        return  Long.toString(System.currentTimeMillis());
    }
}
