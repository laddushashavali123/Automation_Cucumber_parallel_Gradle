package automation.testing.test.cucumber.global;

import com.aventstack.extentreports.ExtentTest;
import automation.testing.util.cucumber.CustomCucumberListener;
import util.LocatorUtils;

public class WorldObject {

    private Configuration configuration;
    private DriverSetup driverSetup;
    private LocatorUtils locatorUtils;
    private CustomCucumberListener CustomCucumberListener;
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

    public void setCucumberTestListener(CustomCucumberListener CustomCucumberListener) {
        this.CustomCucumberListener = CustomCucumberListener;
    }

    public CustomCucumberListener getCustomCucumberListener() {
        return CustomCucumberListener;
    }

    public void setExtentTest(ExtentTest extentTest) {
        this.extentTest = extentTest;
    }

    public ExtentTest getExtentTest() {
        if(CustomCucumberListener!=null) {
            return CustomCucumberListener.getTestStep();
        }
        return extentTest;
    }
}