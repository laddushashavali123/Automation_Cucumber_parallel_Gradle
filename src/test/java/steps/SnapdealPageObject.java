package steps;

import global.DriverSetup;
import global.WorldObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import util.LocatorUtils;

public class SnapdealPageObject extends ShoppingWebSiteObject {

    private By searchBox = By.id("inputValEnter");

    public SnapdealPageObject(WorldObject worldObject) {
        super(worldObject);
    }

    public void search(String searchText) {
        locatorUtils.getElement(searchBox).sendKeys(searchText, Keys.ENTER);
        worldObject.getExtentTest().pass("[Snapdeal] Successfully searched for text: " + searchText);
    }
}
