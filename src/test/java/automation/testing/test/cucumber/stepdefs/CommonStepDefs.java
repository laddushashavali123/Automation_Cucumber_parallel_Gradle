package automation.testing.test.cucumber.stepdefs;

import automation.testing.test.cucumber.global.Configuration;
import automation.testing.test.cucumber.steps.FlipkartPageObject;
import automation.testing.test.cucumber.steps.ShopCluesPageObject;
import automation.testing.test.cucumber.steps.SnapdealPageObject;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import automation.testing.test.cucumber.global.DriverSetup;
import automation.testing.test.cucumber.global.WorldObject;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import automation.testing.util.cucumber.CustomCucumberListener;

import java.net.MalformedURLException;

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
    public void launchesBrowser(DriverSetup.BrowserType browserName) throws MalformedURLException, InterruptedException {
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
                try {
                    driverSetup.getBrowser().get(configuration.shopclues);
                } catch (TimeoutException e) {
                    driverSetup.getBrowser().navigate().refresh();
                    driverSetup.getBrowser().get(configuration.shopclues);
                }
                break;
            case "flipkart":
                try {
                    driverSetup.getBrowser().get(configuration.flipkarturl);
                } catch (TimeoutException e) {
                    driverSetup.getBrowser().navigate().refresh();
                    driverSetup.getBrowser().get(configuration.flipkarturl);
                }
                if (!(driverSetup.getBrowser() instanceof PhantomJSDriver))
                    driverSetup.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//span[text()='Login']//ancestor::div[./button[not(@type)]]/button"))).click();
                break;
            case "snapdeal":
                try {
                    driverSetup.getBrowser().get(configuration.snapdealurl);
                } catch (TimeoutException e) {
                    driverSetup.getBrowser().navigate().refresh();
                    driverSetup.getBrowser().get(configuration.snapdealurl);
                }
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
