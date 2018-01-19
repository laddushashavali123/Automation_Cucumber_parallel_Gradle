package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by mrunal on 7/16/2017.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/ShopCluesTest.feature", glue = "stepdefs",
        plugin = {"html:cucumberResults/cucumber-html-report", "json:cucumberResults/cucumber.json", "util.CustomCucumberListner:ExtentReport.html"}, tags = {"~@Ignore"})
public class JUnitRunner {

}
