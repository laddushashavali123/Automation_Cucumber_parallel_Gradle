package util;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;

import java.io.File;
import java.net.URL;
import java.util.*;

public class CustomCucumberListner implements Formatter, Reporter {

    public static final ExtentReports extentReports = new ExtentReports();
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
        System.out.println("Start of scenario");
        isScenarioStarted = true;
        try {
            System.out.println("==>" + scenario.getName());
            testScenario = testFeature.createNode(new GherkinKeyword("Scenario"), scenario.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void step(Step step) {
        System.out.println("Step");
        if (isScenarioStarted) {
            testSteps.add(step);
        }
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("End of scenario");
        isScenarioStarted = false;
    }

    @Override
    public void done() {
        System.out.println("Done");
    }

    @Override
    public void close() {

    }

    @Override
    public void eof() {
        System.out.println("EOF");
    }

    @Override
    public void before(Match match, Result result) {
//        System.out.println("Before match");
    }

    @Override
    public void result(Result result) {
        System.out.println("Result");
        System.out.println("==>" + result.getStatus() + " " + result.getErrorMessage());
        if (result.getStatus().equalsIgnoreCase("skipped")) {
            testStep.skip("Step Ignored");
            testStep.skip(result.getError());
        }
    }

    @Override
    public void after(Match match, Result result) {
//        System.out.println("After match");
    }

    @Override
    public void match(Match match) {
        System.out.println("At match");
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