package global;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.SeleniumGridUtil;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by mrunal on 7/8/2017.
 */
public class DriverSetup {

    //    @Inject
    private Configuration configuration;
    private WebDriver browser;
    private WebDriverWait wait;
//    private SeleniumGridUtil seleniumGridUtil;

    public DriverSetup(Configuration configuration) { //, SeleniumGridUtil seleniumGridUtil
        this.configuration = configuration;
//        this.seleniumGridUtil = seleniumGridUtil;
    }

    public WebDriver getBrowser() {
        return browser;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public enum BrowserType {
        FIREFOX, CHROME, IE
    }

    public WebDriver initializeRemoteBrowser(BrowserType browserType) throws Exception {
        /*System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\browserBinaries\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\browserBinaries\\chromedriver.exe");
        seleniumGridUtil.startHub();
        seleniumGridUtil.startNode();*/
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
                browser.manage().timeouts().pageLoadTimeout(configuration.maxtimeout, TimeUnit.SECONDS);
                break;
            }
            default: {
                browser = initializeRemoteBrowser(BrowserType.FIREFOX);
                break;
            }
        }
//        browser.manage().timeouts().pageLoadTimeout(configuration.maxtimeout, TimeUnit.SECONDS);
        wait = new WebDriverWait(this.browser, configuration.maxtimeout, configuration.pollinginterval);
        return browser;
    }

    public void closeBrowser() {
        getBrowser().close();
    }
}
