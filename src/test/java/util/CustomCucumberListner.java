package util;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;

import java.net.URL;
import java.util.*;

public class CustomCucumberListner implements Formatter, Reporter {

    private ExtentReports extentReports;
    private ExtentTest testFeature;
    private ExtentTest testScenario;
    public static ExtentTest testStep;
    private Queue<Step> testSteps = new LinkedList<>();
    private Feature currentFeature;
    private boolean isScenarioStarted = false;
    private Map<Thread, CustomCucumberListner> map=new LinkedHashMap<>();

    public CustomCucumberListner(URL someParameter) {
        System.out.println("Listener Object: "+this);
        System.out.println("Thread Object: "+Thread.currentThread().getId());
        map.put(Thread.currentThread(), this);
        extentReports=new ExtentReports();
        ExtentHtmlReporter extentHtmlReporter=new ExtentHtmlReporter(someParameter.getFile());
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle("Test Tile");
        extentHtmlReporter.config().setReportName("Test Report");
        extentReports.attachReporter(extentHtmlReporter);
        extentHtmlReporter.setAppendExisting(true);
    }

    /*public static void setExtentReports(ExtentReports extentReports) {
        CustomCucumberListner.extentReports = extentReports;
    }*/

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
            System.out.println("==>"+scenario.getName());
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
        extentReports.flush();
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