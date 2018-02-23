package automation.testing.test.cucumber.global;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by mrunal on 7/8/2017.
 */
public class DriverSetup {

    public enum BrowserType {
        FIREFOX, CHROME, IE
    }

    private Configuration configuration;
    private WebDriver browser;
    private WebDriverWait wait;
    private ExtentTest extentTest;

    public DriverSetup(Configuration configuration) {
        this.configuration = configuration;
    }

    public WebDriver getBrowser() {
        return browser;
    }

    public WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(this.browser, configuration.maxtimeout, configuration.pollinginterval);
        }
        return wait;
    }

    public void registerExtentTest(ExtentTest extentTest) {
        this.extentTest = extentTest;
    }

    public void log(String message) {
        if (extentTest != null) {
            extentTest.debug(message);
        }
    }

    public WebDriver initializeRemoteBrowser(BrowserType browserType) throws Exception {
        DesiredCapabilities desiredCapabilities;
        switch (browserType) {
            case FIREFOX:
                desiredCapabilities = DesiredCapabilities.firefox();
                break;
            case CHROME:
                desiredCapabilities = DesiredCapabilities.chrome();
                break;
            case IE:
                desiredCapabilities = DesiredCapabilities.internetExplorer();
                break;
            default:
                desiredCapabilities = DesiredCapabilities.firefox();
        }
        browser = new RemoteWebDriver(new URL(configuration.getProperty("hubURL")), desiredCapabilities);
        browser.manage().timeouts().pageLoadTimeout(configuration.maxtimeout, TimeUnit.SECONDS);
        log("Launched [" + browserType + "] browser on Remote Web Driver.");
        return browser;
    }

    public WebDriver initializeBrowser(BrowserType browserType) throws Exception {
        switch (browserType) {
            case FIREFOX: {
                System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\browserBinaries\\geckodriver.exe");
                browser = new FirefoxDriver();
                break;
            }
            case CHROME: {
                System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\browserBinaries\\chromedriver.exe");
                browser = new ChromeDriver();
                break;
            }
            default: {
                browser = initializeRemoteBrowser(BrowserType.FIREFOX);
                break;
            }
        }
        browser.manage().timeouts().pageLoadTimeout(configuration.maxtimeout, TimeUnit.SECONDS);
        log("Launched [" + browserType + "] browser.");
        return browser;
    }

    public void closeBrowser() {
        getBrowser().close();
        log("Successfully closed browser.");
    }
}