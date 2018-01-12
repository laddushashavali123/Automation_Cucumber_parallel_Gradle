package testng;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

public class TestFactory {

    public static ExtentReports extentReports = new ExtentReports();

    @BeforeSuite
    public void beforeSuite() throws ClassNotFoundException {
        ExtentHtmlReporter extentHtmlReporter=new ExtentHtmlReporter("ExtentReport.html");
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Test Tile");
        extentHtmlReporter.config().setReportName("Test Report");
        extentReports.attachReporter(extentHtmlReporter);
        extentHtmlReporter.setAppendExisting(true);
        ExtentTest feature = extentReports.createTest("Feature", "feature description");
        ExtentTest scenario = feature.createNode(new GherkinKeyword("Scenario"),"Scenario", "Scenario description");
        scenario.createNode(new GherkinKeyword("Given"),"Some step A", "Some step description");
        scenario.createNode(new GherkinKeyword("When"),"Some step B", "Some step description");
        scenario.createNode(new GherkinKeyword("Then"),"Some step C", "Some step description");
    }

    @AfterSuite
    public void afterSuite() {
        extentReports.flush();
    }

    @Factory(dataProvider="data")
    public Object[] testFactory(String testName) {
        return new Object[] {new TestClass(testName)};
    }

    @DataProvider(parallel = true)
    public Object[][] data() {
        return new Object[][] {{"Test 1"}, {"Test 2"}, {"Test 3"}};
    }
}
