package stepdefs;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import global.Configuration;
import global.DriverSetup;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import steps.AmazonPageObject;
import steps.FlipkartPageObject;
import steps.SnapdealPageObject;

/**
 * Created by mrunal on 7/16/2017.
 */

public class CommonStepDefs {

    private DriverSetup driverSetup;
    private Configuration configuration;
    private FlipkartPageObject flipkartPageObject;
    private AmazonPageObject amazonPageObject;
    private SnapdealPageObject snapdealPageObject;
    private Scenario scenario;
    private String application;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    public CommonStepDefs(DriverSetup driverSetup, Configuration configuration, FlipkartPageObject flipkartPageObject, AmazonPageObject amazonPageObject, SnapdealPageObject snapdealPageObject) {
        this.driverSetup = driverSetup;
        this.configuration = configuration;
        this.flipkartPageObject = flipkartPageObject;
        this.amazonPageObject = amazonPageObject;
        this.snapdealPageObject = snapdealPageObject;
    }

    @Given("^user launches \"([^\"]*)\" browser$")
    public void launchesBrowser(DriverSetup.BrowserType browserName) {
        driverSetup.initializeBrowser(browserName);
    }

    @Then("^user opens \"([^\"]*)\" app$")
    public void openApp(String appName) {
        this.application = appName;
        switch (appName) {
            case "amazon":
                driverSetup.getBrowser().get(configuration.amazonurl);
                break;
            case "flipkart":
                driverSetup.getBrowser().get(configuration.flipkarturl);
                break;
            case "snapdeal":
                driverSetup.getBrowser().get(configuration.snapdealurl);
                break;
            default:
                break;
        }
    }

    @Then("^user searches for \"([^\"]*)\"$")
    public void search(String searchText) {
        switch (application) {
            case "amazon":
                amazonPageObject.search(searchText);
                break;
            case "flipkart":
                flipkartPageObject.search(searchText);
                break;
            case "snapdeal":
                snapdealPageObject.search(searchText);
                break;
            default:
                System.out.println("Not yet supported.");
        }
    }

    @Then("^user takes screen shot of the results$")
    public void takeScreenShot() {
        scenario.embed(((TakesScreenshot) driverSetup.getBrowser()).getScreenshotAs(OutputType.BYTES), "image/JPEG");
    }

    @And("^closes the browser$")
    public void closeBrowser() {
        driverSetup.closeBrowser();
    }
}
