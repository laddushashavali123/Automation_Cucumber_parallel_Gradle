package automation.testing.test.cucumber.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by mrunal on 7/16/2017.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/", glue = "automation/testing/test/cucumber/stepdefs", plugin = {"json:cucumber-parallel-execution-results/jsonCucumberResults/", "html:cucumber-parallel-execution-results/htmlCucumberResults/"}, tags = {"~@Ignore"})
public class JUnitRunnerTemplate {

}
