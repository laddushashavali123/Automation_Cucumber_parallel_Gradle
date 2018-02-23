package automation.testing.util.cucumber;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class CustomCucumberListener implements Formatter, Reporter {

    public static boolean isReporterStarted = false;
    public static ExtentReports extentReports;
    public static Map<Thread, CustomCucumberListener> map = new LinkedHashMap<>();

    private ExtentTest testFeature;
    private ExtentTest testScenario;
    private ExtentTest testStep;
    private Queue<Step> testSteps = new LinkedList<>();
    private boolean isScenarioStarted = false;

    public CustomCucumberListener() {
        synchronized (CustomCucumberListener.class) {
            if (extentReports == null) {
                setDefaultExtentReporter();
            }
            map.put(Thread.currentThread(), this);
        }
    }

    private void setDefaultExtentReporter() {
        CustomCucumberListener.isReporterStarted = true;
        extentReports = new ExtentReports();
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter("ExtentReport.html");
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Test Report Title");
        extentHtmlReporter.config().setReportName("Test Report Name");
        CustomCucumberListener.extentReports.attachReporter(extentHtmlReporter);
    }

    public ExtentTest getTestStep() {
        return testStep;
    }

    public ExtentTest getTestScenario() {
        return testScenario;
    }

    public ExtentTest getTestFeature() {
        return testFeature;
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {

    }

    @Override
    public void uri(String uri) {

    }

    @Override
    public void feature(Feature feature) {
        if (!isReporterStarted)
            return;
        synchronized (extentReports) {
            testFeature = extentReports.createTest(feature.getName(), feature.getDescription());
        }
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    @Override
    public void examples(Examples examples) {

    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {

    }

    @Override
    public void background(Background background) {

    }

    @Override
    public void scenario(Scenario scenario) {
        if (!isReporterStarted)
            return;
        isScenarioStarted = true;
        try {
            testScenario = testFeature.createNode(new GherkinKeyword("Scenario"), scenario.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void step(Step step) {
        if (!isReporterStarted)
            return;
        if (isScenarioStarted) {
            testSteps.add(step);
        }
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        if (!isReporterStarted)
            return;
        isScenarioStarted = false;
    }

    @Override
    public void done() {

    }

    @Override
    public void close() {
        synchronized (extentReports) {
            extentReports.flush();
        }
    }

    @Override
    public void eof() {

    }

    @Override
    public void before(Match match, Result result) {

    }

    @Override
    public void result(Result result) {
        if (!isReporterStarted)
            return;
        if (result.getStatus().equalsIgnoreCase("skipped")) {
            testStep.skip("Step Ignored / Skipped");
        } else if (result.getStatus().equalsIgnoreCase("failed")) {
            testStep.fail(result.getError());
        } else if (result.getStatus().equalsIgnoreCase("undefined")) {
            testStep.skip("Step Definition missing for this step.");
        }
    }

    @Override
    public void after(Match match, Result result) {

    }

    @Override
    public void match(Match match) {
        if (!isReporterStarted)
            return;
        if (!isScenarioStarted)
            return;
        Step step = testSteps.poll();
        String stepName = step.getName();
        if (step.getRows() != null) {
            StringBuilder table = new StringBuilder();
            for (DataTableRow row :
                    step.getRows()) {
                table.append("|").append(StringUtils.join(row.getCells(), "|")).append("|");
            }
            stepName += "<br>" + table;
        } else if (step.getDocString() != null) {
            stepName += " " + step.getDocString().getValue();
        }
        try {
            testStep = testScenario.createNode(new GherkinKeyword(step.getKeyword()), stepName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void embedding(String mimeType, byte[] data) {

    }

    @Override
    public void write(String text) {

    }
}