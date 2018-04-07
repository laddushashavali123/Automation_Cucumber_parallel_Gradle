package automation.testing.test.cucumber.test;

import automation.testing.util.cucumber.CucumberParallelRunner;
import org.junit.Assert;
import org.junit.runner.Result;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class CucumberParallelRunnerTest {

    @BeforeTest
    public void setUp() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM phantomjs.exe");
    }

    @AfterTest
    public void tearDown() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM phantomjs.exe");
    }

    @Test(priority = 1)
    @Parameters({"numberOfThreads"})
    public void cucumberParallelScenarioTest(int numberOfThreads) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, IOException {
        File resultsDir = new File("cucumber-parallel-execution-results");
        resultsDir.mkdirs();
        CucumberParallelRunner runner = new CucumberParallelRunner();
        Result result = runner
                .withExtentReporter("cucumber-parallel-execution-results/extent-report-parallel-scenario.html", "Parallel Scenario Execution Report", "Cucumber Parallel Scenario Execution Test Result")
                .withCucumberReporting()
                .withParallel(CucumberParallelRunner.ParallelType.SCENARIO)
                .configureParallelScenarioExecution(ParallelScenarioRunnerTemplate.class.getCanonicalName())
                .assembleParallelRunner(ParallelScenarioRunnerTemplate.class.getCanonicalName())
                .run(numberOfThreads)
                .cucumberReport("cucumber-parallel-execution-results/parallel-scenario-report", "Cucumber Parallel Scenario Execution Test Result").getResults();
        Assert.assertTrue(result.wasSuccessful());
    }

    @Test(priority = 2)
    @Parameters({"numberOfThreads"})
    public void cucumberParallelFeatureTest(int numberOfThreads) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, IOException {
        File resultsDir = new File("cucumber-parallel-execution-results");
        resultsDir.mkdirs();
        CucumberParallelRunner runner = new CucumberParallelRunner();
        Result result = runner
                .withExtentReporter("cucumber-parallel-execution-results/extent-report-parallel-feature.html", "Parallel Feature Execution Report", "Cucumber Parallel Feature Execution Test Result")
                .withCucumberReporting()
                .withParallel(CucumberParallelRunner.ParallelType.FEATURE)
                .configureParallelScenarioExecution(ParallelFeatureRunnerTemplate.class.getCanonicalName())
                .assembleParallelRunner(ParallelFeatureRunnerTemplate.class.getCanonicalName())
                .run(numberOfThreads)
                .cucumberReport("cucumber-parallel-execution-results/parallel-feature-report", "Cucumber Parallel Feature Execution Test Result").getResults();
        Assert.assertTrue(result.wasSuccessful());
    }
}
