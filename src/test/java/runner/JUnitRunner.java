package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by mrunal on 7/16/2017.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/", glue = "stepdefs", plugin = {"json:jsonCucumberResults/", "html:htmlCucumberResults/"}, tags = {"~@Ignore"})
public class JUnitRunner {

}
