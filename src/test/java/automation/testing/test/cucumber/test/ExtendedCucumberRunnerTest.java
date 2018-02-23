package automation.testing.test.cucumber.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.github.mkolisnyk.cucumber.runner.*;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import automation.testing.util.cucumber.CustomCucumberListener;

@RunWith(ExtendedParallelCucumber.class)
@ExtendedCucumberOptions(threadsCount = 3, outputFolder = "results/", jsonReport = "results/cucumber.json", retryCount = 1
        , detailedReport = true, detailedAggregatedReport = true, overviewReport = true, overviewChartsReport = true, featureOverviewChart = true, coverageReport = true,
        consolidatedReport = true, consolidatedReportConfig = "src/test/resources/conf/consolidated_batch.json"
)//, usageReport = true,jsonUsageReport = "results/cukes-usage.json"
@CucumberOptions(features = "src/test/resources/features", glue = "automation.testing.test.cucumber.stepdefs", plugin = {"automation.testing.util.cucumber.CustomCucumberListener:results/ExtentReport.html", "html:results/cucumber-html-report", "json:results/cucumber.json",
        "usage:results/cukes-usage.json"})
public class ExtendedCucumberRunnerTest {

    @BeforeSuite
    public static void setUp() {
        CustomCucumberListener.isReporterStarted = true;
        CustomCucumberListener.extentReports=new ExtentReports();
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter("results/ExtentReport.html");
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Test Title");
        extentHtmlReporter.config().setReportName("Test Report");
        CustomCucumberListener.extentReports.attachReporter(extentHtmlReporter);
    }

    @AfterSuite
    public static void tearDown() {
        synchronized (CustomCucumberListener.class) {
            CustomCucumberListener.extentReports.flush();
        }
    }

}