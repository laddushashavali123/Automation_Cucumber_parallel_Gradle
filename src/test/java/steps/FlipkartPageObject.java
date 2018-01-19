package steps;

import global.DriverSetup;
import global.WorldObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import util.LocatorUtils;

/**
 * Created by mrunal on 7/15/2017.
 */
public class FlipkartPageObject extends ShoppingWebSiteObject {

    private By searchBox = By.name("q");

    public FlipkartPageObject(WorldObject worldObject) {
        super(worldObject);
    }

    public void search(String searchText) {
        locatorUtils.getElement(searchBox).sendKeys(searchText, Keys.ENTER);
        worldObject.getExtentTest().pass("[Flipkart] Successfully searched for text: " + searchText);
    }
}
