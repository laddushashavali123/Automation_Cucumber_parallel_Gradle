package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrunal on 7/16/2017.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/", glue = "stepdefs", plugin = {"html:results/cucumber-html-report", "util.CustomCucumberListner"}, tags = {"~@Ignore"})
public class JUnitRunner {

    @AfterClass
    public static void afterClass() {
        File reportOutputDirectory = new File("results");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("results/cucumber.json");
        String buildNumber = "1";
        String projectName = "cucumberProject";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setParallelTesting(false);
        configuration.setRunWithJenkins(false);
        configuration.setBuildNumber(buildNumber);
        configuration.addClassifications("Platform", "Windows");
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Branch", "release/1.0");
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
