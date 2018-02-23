package automation.testing.test.cucumber.steps;

import automation.testing.test.cucumber.global.WorldObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

/**
 * Created by mrunal on 7/29/2017.
 */
public class ShopCluesPageObject extends ShoppingWebSiteObject {

    private By searchBox = By.id("autocomplete");

    public ShopCluesPageObject(WorldObject worldObject) {
        super(worldObject);
    }

    public void search(String searchText) {
        locatorUtils.getElement(searchBox).sendKeys(searchText, Keys.ENTER);
        worldObject.getExtentTest().pass("[Shopclues] Successfully searched for text: " + searchText);
    }
}
