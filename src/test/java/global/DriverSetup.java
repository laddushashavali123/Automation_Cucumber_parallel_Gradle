package global;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by mrunal on 7/8/2017.
 */
public class DriverSetup {

//    @Inject
    private Configuration configuration;
    private WebDriver browser;
    private WebDriverWait wait;

    public DriverSetup(Configuration configuration) {
        this.configuration = configuration;
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

    public WebDriver initializeBrowser(BrowserType browserType) {
        WebDriver browser;
        switch (browserType) {
            case FIREFOX: {
                System.setProperty("webdriver.gecko.driver","I:\\ideaworkspace\\cucumber-spring-practise\\src\\test\\resources\\browserBinaries\\geckodriver.exe");
                FirefoxOptions ffOptions = new FirefoxOptions();
                ffOptions.setBinary("D:\\installations\\browsers\\ff\\52.0.2_32\\firefox.exe");
                browser = new FirefoxDriver(ffOptions);
                break;
            }
            case CHROME: {
                System.setProperty("webdriver.chrome.driver","I:\\ideaworkspace\\cucumber-spring-practise\\src\\test\\resources\\browserBinaries\\chromedriver.exe");
                browser = new ChromeDriver();
                break;
            }
            default: {
                System.setProperty("webdriver.gecko.driver","I:\\ideaworkspace\\cucumber-spring-practise\\src\\test\\resources\\browserBinaries\\geckodriver.exe");
                FirefoxOptions ffOptions = new FirefoxOptions();
                ffOptions.setBinary("D:\\installations\\browsers\\ff\\52.0.2_32\\firefox.exe");
                browser = new FirefoxDriver(ffOptions);
                break;
            }
        }
        this.browser = browser;
        this.browser.manage().timeouts().pageLoadTimeout(configuration.maxtimeout, TimeUnit.SECONDS);
        this.wait = new WebDriverWait(this.browser,configuration.maxtimeout, configuration.pollinginterval);
        return browser;
    }

    public void closeBrowser() {
        getBrowser().close();
    }
}
