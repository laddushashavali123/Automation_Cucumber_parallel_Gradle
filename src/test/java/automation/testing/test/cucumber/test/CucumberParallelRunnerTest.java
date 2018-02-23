package automation.testing.test.cucumber.test;

import automation.testing.util.cucumber.CucumberParallelRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;

import java.lang.reflect.InvocationTargetException;

public class CucumberParallelRunnerTest {

    @Test
    public void test() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        CucumberParallelRunner runner = new CucumberParallelRunner();
        Result result = runner
                .withExtentReporter("ExtentReport.html", "Test Document Title", "Test Report Name")
                .withCucumberReporting()
                .withParallel(CucumberParallelRunner.ParallelType.SCENARIO)
                .configureParallelScenarioExecution(JUnitRunnerTemplate.class.getCanonicalName())
                .assembleParallelRunner(JUnitRunnerTemplate.class.getCanonicalName())
                .run(4)
                .cucumberReport("cucumber-report", "Cucumber Report").getResults();
        Assert.assertTrue(result.wasSuccessful());
    }
}
