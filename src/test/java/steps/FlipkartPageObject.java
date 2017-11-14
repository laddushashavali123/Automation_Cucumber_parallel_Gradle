package steps;

import global.DriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import util.LocatorUtils;

/**
 * Created by mrunal on 7/15/2017.
 */
public class FlipkartPageObject {
    private By searchBox = By.name("q");

    private DriverSetup driverSetup;
    private LocatorUtils locatorUtils;

    public FlipkartPageObject(DriverSetup driverSetup, LocatorUtils locatorUtils) {
        this.driverSetup = driverSetup;
        this.locatorUtils = locatorUtils;
    }

    public void search(String searchText) {
        locatorUtils.getElement(searchBox).sendKeys(searchText, Keys.ENTER);
    }
}
