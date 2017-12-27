package runner;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class PageLoadTimeoutTest {

    @Test
    public void test() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\browserBinaries\\geckodriver.exe");
        WebDriver browser = new FirefoxDriver();
        browser.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        browser.manage().window().maximize();
        browser.get("http://www.snapdeal.com");
        System.out.println("Wait");
        browser.close();
    }
}
