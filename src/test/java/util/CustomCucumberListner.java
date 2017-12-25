package util;

import com.aventstack.extentreports.*;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CustomCucumberListner implements Formatter, Reporter {

    private static ExtentReports extentReports;
    public static ExtentTest testFeature;
    public static ExtentTest testScenario;
    public static ExtentTest testStep;
    private Queue<Step> testSteps = new LinkedList<>();
    private Feature currentFeature;
    private boolean isScenarioStarted = false;

    public static void setExtentReports(ExtentReports extentReports) {
        CustomCucumberListner.extentReports = extentReports;
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {

    }

    @Override
    public void uri(String uri) {

    }

    @Override
    public void feature(Feature feature) {
        System.out.println("Feature");
        currentFeature = feature;
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
        if(testFeature==null) {
            testFeature = extentReports.createTest(currentFeature.getName(), currentFeature.getDescription());
        }
        System.out.println("Start of scenario");
        isScenarioStarted = true;
        try {
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
        System.out.println("Close");
    }

    @Override
    public void eof() {
        System.out.println("EOF");
        testFeature=null;
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

    @Override
    public void embedding(String mimeType, byte[] data) {

    }

    @Override
    public void write(String text) {

    }
}