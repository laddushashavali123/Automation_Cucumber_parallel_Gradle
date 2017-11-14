package runner;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import com.github.mkolisnyk.cucumber.runner.ExtendedParallelCucumber;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(ExtendedParallelCucumber.class)
@ExtendedCucumberOptions(threadsCount = 2, outputFolder = "results/", jsonReport = "results/cucumber.json"
        , detailedReport = true, detailedAggregatedReport = true, overviewReport = true, overviewChartsReport = true, featureOverviewChart = true, coverageReport = true, consolidatedReport = true, consolidatedReportConfig = "src/test/resources/conf/consolidated_batch.json")
@CucumberOptions(features = "src/test/resources/features", glue = "stepdefs", plugin = {"html:results/cucumber-html-report", "json:results/cucumber.json", "usage:results/cukes-usage.json"})
public class ExtendedCucumberRunnerTest {


}
