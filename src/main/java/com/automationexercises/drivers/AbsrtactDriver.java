package com.automationexercises.drivers;

import com.automationexercises.utils.dataReader.PropertyReader;
import org.openqa.selenium.WebDriver;

public abstract class AbsrtactDriver {

    protected final static String remotehost = PropertyReader.getProperty("remoteHost");
    protected final static String remoteport = PropertyReader.getProperty("remotePort");

    protected String downlaodpath = System.getProperty("user.dir") + "\\test\\Downloads";
    public abstract WebDriver createDriver();


}
