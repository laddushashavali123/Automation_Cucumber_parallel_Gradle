package testng;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import org.testng.annotations.Test;

import java.io.File;

public class ExtentTestNGTest {

    @Test
    public void test1() throws ClassNotFoundException {
        ExtentReports extentReports = new ExtentReports();
        File reportFileLocation = new File("ExtentReport.html");
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(reportFileLocation.getName());
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Test Tile");
        extentHtmlReporter.config().setReportName("Test Report");
        extentHtmlReporter.setAppendExisting(true);
        extentReports.attachReporter(extentHtmlReporter);
        ExtentTest feature = extentReports.createTest("Test 1");
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Test Scenario 1");
        ExtentTest step = scenario.createNode(new GherkinKeyword("Given"), "some step");
        extentReports.flush();
    }

    @Test
    public void test2() throws ClassNotFoundException {
        ExtentReports extentReports = new ExtentReports();
        File reportFileLocation = new File("ExtentReport.html");
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(reportFileLocation.getName());
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Test Tile");
        extentHtmlReporter.config().setReportName("Test Report");
        extentHtmlReporter.setAppendExisting(true);
        extentReports.attachReporter(extentHtmlReporter);
        ExtentTest feature = extentReports.createTest("Test 2");
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"), "Test Scenario 2");
        ExtentTest step = scenario.createNode(new GherkinKeyword("Given"), "some step");
        extentReports.flush();
    }
}
