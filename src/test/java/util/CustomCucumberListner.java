package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import runner.ExtendedCucumberRunnerTest;

import java.util.*;

public class CustomCucumberListner implements Formatter, Reporter {

    public static final ExtentReports extentReports = new ExtentReports();
    public static boolean isReporterStarted = false;
    private ExtentTest testFeature;
    private ExtentTest testScenario;
    private ExtentTest testStep;
    private Queue<Step> testSteps = new LinkedList<>();
    private boolean isScenarioStarted = false;
    public static Map<Thread, CustomCucumberListner> map = new LinkedHashMap<>();

    public CustomCucumberListner() {
        synchronized (CustomCucumberListner.class) {
            map.put(Thread.currentThread(), this);
        }
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
            testStep.skip("Step Ignored");
            testStep.skip(result.getError());
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
        try {
            testStep = testScenario.createNode(new GherkinKeyword(step.getKeyword()), step.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ExtentTest getTestStep() {
        return testStep;
    }

    @Override
    public void embedding(String mimeType, byte[] data) {

    }

    @Override
    public void write(String text) {

    }
}
