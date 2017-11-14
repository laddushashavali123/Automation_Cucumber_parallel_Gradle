package steps;

import global.DriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import util.LocatorUtils;

public class SnapdealPageObject {

    private By searchBox = By.id("inputValEnter");

    private DriverSetup driverSetup;
    private LocatorUtils locatorUtils;

    public SnapdealPageObject(DriverSetup driverSetup, LocatorUtils locatorUtils) {
        this.driverSetup = driverSetup;
        this.locatorUtils = locatorUtils;
    }

    public void search(String searchText) {
        locatorUtils.getElement(searchBox).sendKeys(searchText, Keys.ENTER);
    }
}
