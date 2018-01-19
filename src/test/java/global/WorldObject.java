package global;

import com.aventstack.extentreports.ExtentTest;
import util.CustomCucumberListner;
import util.LocatorUtils;

public class WorldObject {

    private Configuration configuration;
    private DriverSetup driverSetup;
    private LocatorUtils locatorUtils;
    private CustomCucumberListner customCucumberListner;
    private ExtentTest extentTest;

    public WorldObject(Configuration configuration, DriverSetup driverSetup, LocatorUtils locatorUtils) {
        this.configuration = configuration;
        this.driverSetup = driverSetup;
        this.locatorUtils = locatorUtils;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public DriverSetup getDriverSetup() {
        return driverSetup;
    }

    public void setDriverSetup(DriverSetup driverSetup) {
        this.driverSetup = driverSetup;
    }

    public LocatorUtils getLocatorUtils() {
        return locatorUtils;
    }

    public void setLocatorUtils(LocatorUtils locatorUtils) {
        this.locatorUtils = locatorUtils;
    }

    public void setCucumberTestListener(CustomCucumberListner customCucumberListner) {
        this.customCucumberListner = customCucumberListner;
    }

    public CustomCucumberListner getCustomCucumberListner() {
        return customCucumberListner;
    }

    public void setExtentTest(ExtentTest extentTest) {
        this.extentTest = extentTest;
    }

    public ExtentTest getExtentTest() {
        if(customCucumberListner!=null) {
            return customCucumberListner.getTestStep();
        }
        return extentTest;
    }
}