package runner;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.github.mkolisnyk.cucumber.runner.AfterSuite;
import com.github.mkolisnyk.cucumber.runner.BeforeSuite;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import com.github.mkolisnyk.cucumber.runner.ExtendedParallelScenarioCucumber;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import util.CustomCucumberListner;

@RunWith(ExtendedParallelScenarioCucumber.class)
@ExtendedCucumberOptions(threadsCount = 4, outputFolder = "results/", jsonReport = "results/cucumber.json", retryCount = 1
        , detailedReport = true, detailedAggregatedReport = true, overviewReport = true, overviewChartsReport = true, featureOverviewChart = true, coverageReport = true,
        consolidatedReport = true, consolidatedReportConfig = "src/test/resources/conf/consolidated_batch.json"
)//, usageReport = true,jsonUsageReport = "results/cukes-usage.json"
@CucumberOptions(features = "src/test/resources/features", glue = "stepdefs", plugin = {"util.CustomCucumberListner:results/ExtentReport.html", "html:results/cucumber-html-report", "json:results/cucumber.json",
        "usage:results/cukes-usage.json"})
public class ExtendParallelScenarioRunnerTest {

    @BeforeSuite
    public static void setUp() {
        CustomCucumberListner.isReporterStarted = true;
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter("ExtentReport.html");
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Test Tile");
        extentHtmlReporter.config().setReportName("Test Report");
//        extentHtmlReporter.setAppendExisting(true);
        CustomCucumberListner.extentReports.attachReporter(extentHtmlReporter);
    }

    @AfterSuite
    public static void tearDown() {
        synchronized (CustomCucumberListner.class) {
            CustomCucumberListner.extentReports.flush();
        }
    }
}
