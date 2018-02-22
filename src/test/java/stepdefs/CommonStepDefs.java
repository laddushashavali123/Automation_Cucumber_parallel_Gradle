package stepdefs;

import com.github.mkolisnyk.cucumber.runner.AfterSuite;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import global.Configuration;
import global.DriverSetup;
import global.WorldObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import steps.ShopCluesPageObject;
import steps.FlipkartPageObject;
import steps.SnapdealPageObject;
import util.CustomCucumberListener;

/**
 * Created by mrunal on 7/16/2017.
 */

public class CommonStepDefs {

    private DriverSetup driverSetup;
    private Configuration configuration;
    private WorldObject worldObject;
    private FlipkartPageObject flipkartPageObject;
    private ShopCluesPageObject shopcluesPageObject;
    private SnapdealPageObject snapdealPageObject;
    private Scenario scenario;
    private String application;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        worldObject.setCucumberTestListener(CustomCucumberListener.map.get(Thread.currentThread()));
    }

    public CommonStepDefs(WorldObject worldObject, FlipkartPageObject flipkartPageObject, ShopCluesPageObject shopcluesPageObject, SnapdealPageObject snapdealPageObject) {
        this.worldObject = worldObject;
        this.driverSetup = worldObject.getDriverSetup();
        this.configuration = worldObject.getConfiguration();
        this.flipkartPageObject = flipkartPageObject;
        this.shopcluesPageObject = shopcluesPageObject;
        this.snapdealPageObject = snapdealPageObject;
    }

    @Given("^user launches \"([^\"]*)\" browser$")
    public void launchesBrowser(DriverSetup.BrowserType browserName) throws Exception {
        if (Boolean.valueOf(configuration.getProperty("useGrid"))) {
            driverSetup.initializeRemoteBrowser(browserName);
        } else {
            driverSetup.initializeBrowser(browserName);
        }
    }

    @Then("^user opens \"([^\"]*)\" app$")
    public void openApp(String appName) {
        this.application = appName;
        switch (appName) {
            case "shopclues":
                driverSetup.getBrowser().get(configuration.shopclues);
                break;
            case "flipkart":
                driverSetup.getBrowser().get(configuration.flipkarturl);
                driverSetup.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//span[text()='Login']//ancestor::div[./button[not(@type)]]/button"))).click();
                break;
            case "snapdeal":
                driverSetup.getBrowser().get(configuration.snapdealurl);
                break;
            default:
                break;
        }
        worldObject.getExtentTest().pass("Successfully opened " + appName + " shopping portal.");
    }

    @Then("^user searches for \"([^\"]*)\"$")
    public void search(String searchText) {
        switch (application) {
            case "shopclues":
                shopcluesPageObject.search(searchText);
                break;
            case "flipkart":
                flipkartPageObject.search(searchText);
                break;
            case "snapdeal":
                /*if(searchText.equalsIgnoreCase("dell")) {
                    Assert.fail("Purpose fail");
                }*/
                snapdealPageObject.search(searchText);
                break;
            default:
                System.out.println("Not yet supported.");
        }
    }

    @Then("^user takes screen shot of the results$")
    public void takeScreenShot() {
        scenario.embed(((TakesScreenshot) driverSetup.getBrowser()).getScreenshotAs(OutputType.BYTES), "image/png");
        worldObject.getExtentTest().pass("Successfully taken screen shot.");
    }

    @And("^closes the browser$")
    public void closeBrowser() {
        driverSetup.closeBrowser();
    }
}
