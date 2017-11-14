package util;

import global.Configuration;
import global.DriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by mrunal on 7/29/2017.
 */
public class LocatorUtils {

    private DriverSetup driverSetup;
    private Configuration configuration;

    public LocatorUtils(DriverSetup driverSetup, Configuration configuration) {
        this.driverSetup = driverSetup;
        this.configuration = configuration;
    }

    public WebElement getElement(By locator) {
        return driverSetup.getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> getElements(By locator) {
        return driverSetup.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public WebElement getElement(WebElement element, By locator) {
        Wait<WebElement> wait = new FluentWait<WebElement>(element).withTimeout(configuration.maxtimeout, TimeUnit.SECONDS).pollingEvery(configuration.pollinginterval, TimeUnit.MILLISECONDS);
        return wait.until(new Function<WebElement, WebElement>() {
            @Override
            public WebElement apply(WebElement element) {
                return element.findElement(locator);
            }
        });
    }

    public List<WebElement> getElements(WebElement element, By locator) {
        Wait<WebElement> wait = new FluentWait<WebElement>(element).withTimeout(configuration.maxtimeout, TimeUnit.SECONDS).pollingEvery(configuration.pollinginterval, TimeUnit.MILLISECONDS);
        return wait.until(element1 -> element1.findElements(locator));
    }
}
