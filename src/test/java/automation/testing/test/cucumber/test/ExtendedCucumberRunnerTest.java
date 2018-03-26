package automation.testing.test.cucumber.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.github.mkolisnyk.cucumber.runner.*;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import automation.testing.util.cucumber.CustomCucumberListener;

import java.io.IOException;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(outputFolder = "cucumber-parallel-execution-results/", jsonReport = "cucumber-parallel-execution-results/cucumber-json-report.json", retryCount = 0
        , detailedReport = true, detailedAggregatedReport = true, overviewReport = true, overviewChartsReport = true, featureOverviewChart = true, coverageReport = true,
        consolidatedReport = true, consolidatedReportConfig = "src/test/resources/conf/consolidated_batch.json", usageReport = true, jsonUsageReport = "cucumber-parallel-execution-results/cucumber-json-usage-report.json")
@CucumberOptions(features = "src/test/resources/features", glue = "automation.testing.test.cucumber.stepdefs", plugin =
        {"automation.testing.util.cucumber.CustomCucumberListener:cucumber-parallel-execution-results/extent-report-cucumber-report-based.html"
        , "html:cucumber-parallel-execution-results/cucumber-html-report"
        , "json:cucumber-parallel-execution-results/cucumber-json-report.json"
        , "usage:cucumber-parallel-execution-results/cucumber-json-usage-report.json"}, tags = {"@cucumber-report"})
public class ExtendedCucumberRunnerTest {

    @BeforeSuite
    public static void setUp() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM phantomjs.exe");
        CustomCucumberListener.isReporterStarted = true;
        CustomCucumberListener.extentReports = new ExtentReports();
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter("cucumber-parallel-execution-results/extent-report-cucumber-report-based.html");
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Parallel Execution Report from Cucumber Report Library");
        extentHtmlReporter.config().setReportName("Cucumber Parallel Execution Report");
        CustomCucumberListener.extentReports.attachReporter(extentHtmlReporter);
    }

    @AfterSuite
    public static void tearDown() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM phantomjs.exe");
        synchronized (CustomCucumberListener.class) {
            CustomCucumberListener.extentReports.flush();
        }
    }

}