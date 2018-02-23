package automation.testing.test.cucumber.steps;

import automation.testing.test.cucumber.global.Configuration;
import automation.testing.test.cucumber.global.DriverSetup;
import automation.testing.test.cucumber.global.WorldObject;
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
