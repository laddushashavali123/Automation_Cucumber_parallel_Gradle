package runner;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class PageLoadTimeoutTest {

    @Test
    public void test() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\browserBinaries\\geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
        WebDriver browser = new FirefoxDriver(firefoxOptions);
        browser.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        browser.manage().window().maximize();
        browser.get("http://www.snapdeal.com");
        System.out.println("Wait");
        browser.close();
    }
}
