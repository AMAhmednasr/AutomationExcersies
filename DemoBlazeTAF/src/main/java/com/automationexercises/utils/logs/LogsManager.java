package com.automationexercises.utils.logs;

import com.automationexercises.utils.report.AllureConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

public class LogsManager {


    public static final String LOGS_PATH = AllureConstant.USER_DIR + "test-output/Logs/";

    public static Logger logger(){
            return  LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
        }

        // info("errorMessage", e.getMessage());
        public static void info(String... message){
            logger().info(String.join(" ", message));
         }

        public static void error(String... message){
            logger().error(String.join(" ", message));
        }

        public static void warn(String... message){
            logger().warn(String.join(" ", message));
        }

        public static void fatal(String... message){
            logger().fatal(String.join(" ", message));
        }

        public static void debug(String... message){
            logger().debug(String.join(" ", message));
        }

    public static void shutdown() {
        LogManager.shutdown();
    }

    public static LoggerContext getContext(boolean currentContext) {
        return (LoggerContext) LogManager.getContext(currentContext);
    }
    }


