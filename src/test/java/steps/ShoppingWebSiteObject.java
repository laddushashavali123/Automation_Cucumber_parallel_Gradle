package steps;

import com.aventstack.extentreports.ExtentTest;
import global.Configuration;
import global.DriverSetup;
import global.WorldObject;
import util.LocatorUtils;

public abstract class ShoppingWebSiteObject {

    protected Configuration configuration;
    protected DriverSetup driverSetup;
    protected LocatorUtils locatorUtils;
    protected WorldObject worldObject;

    public ShoppingWebSiteObject(WorldObject worldObject) {
        this.worldObject = worldObject;
        this.configuration = worldObject.getConfiguration();
        this.driverSetup = worldObject.getDriverSetup();
        this.locatorUtils = worldObject.getLocatorUtils();
    }
}
