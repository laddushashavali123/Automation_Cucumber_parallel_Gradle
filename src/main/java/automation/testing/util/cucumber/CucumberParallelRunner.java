package automation.testing.util.cucumber;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This utility class provides user option to run cucumber test in parallel: <br/>
 * a) User can run features in parallel <br/>
 * b) User can run scenarios in parallel <br/>
 *
 * @author mrunal
 */
public class CucumberParallelRunner {

    public enum ParallelType {
        FEATURE, SCENARIO;
    }

    private static List<File> featureFiles;
    private static int counter = -1;

    private ParallelType parallelType = ParallelType.FEATURE;
    private List<Class<?>> featureRunners = new ArrayList<>();
    private List<String> jsonResults = new ArrayList<>();
    private Result results;
    private boolean startExtentReporter = false;
    private boolean startCucumberReporting = false;

    public Result getResults() {
        return this.results;
    }

    /**
     * @param parallelType
     * @return
     */
    public CucumberParallelRunner withParallel(ParallelType parallelType) {
        this.parallelType = parallelType;
        return this;
    }

    /**
     * @param reportFilePath
     * @param documentTitle
     * @param reportName
     * @return
     */
    public CucumberParallelRunner withExtentReporter(String reportFilePath, String documentTitle, String reportName) {
        startExtentReporter = true;
        CustomCucumberListener.isReporterStarted = true;
        CustomCucumberListener.extentReports = new ExtentReports();
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(reportFilePath);
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        extentHtmlReporter.config().setDocumentTitle(documentTitle);
        extentHtmlReporter.config().setReportName(reportName);
        CustomCucumberListener.extentReports.attachReporter(extentHtmlReporter);
        return this;
    }

    /**
     * @return
     */
    public CucumberParallelRunner withCucumberReporting() {
        startCucumberReporting = true;
        return this;
    }

    /**
     * @param templateRunnerClassName - FQN of Class name of the template Junit Runner class which will
     *                                specify the feature files directory location, reporting format
     *                                etc.,
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public CucumberParallelRunner assembleParallelRunner(String templateRunnerClassName) throws ClassNotFoundException, NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> originalRunnerClass = Class.forName(templateRunnerClassName);
        CucumberOptions originalAnnotation = originalRunnerClass.getAnnotation(CucumberOptions.class);
        if (parallelType == ParallelType.FEATURE)
            featureFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File(originalAnnotation.features()[0]).listFiles())));
        else if (parallelType == ParallelType.SCENARIO)
            featureFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(ParallelScenarioBuilder.dir.listFiles())));
        CucumberOptions newAnnotation = new CucumberOptions() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return originalAnnotation.annotationType();
            }

            @Override
            public String[] tags() {
                return originalAnnotation.tags();
            }

            @Override
            public boolean strict() {
                return originalAnnotation.strict();
            }

            @Override
            public SnippetType snippets() {
                return originalAnnotation.snippets();
            }

            @Override
            public String[] junit() {
                return originalAnnotation.junit();
            }

            @Override
            public String[] plugin() {
                counter++;
                String[] plugins = originalAnnotation.plugin();
                for (int i = 0; i < plugins.length; i++) {
                    String plugin = originalAnnotation.plugin()[i];
                    if (plugin.toLowerCase().contains("html:")) {
                        String reportPath = plugin.substring(plugin.indexOf(":") + 1, plugin.lastIndexOf("/"));
                        reportPath = reportPath + File.separator + featureFiles.get(counter).getName().substring(0,
                                featureFiles.get(counter).getName().lastIndexOf("."));
                        plugins[i] = "html:" + reportPath;
                    } else if (plugin.toLowerCase().contains("json")) {
                        String reportPath = plugin.substring(plugin.indexOf(":") + 1, plugin.lastIndexOf("/"));
                        reportPath = reportPath + File.separator + featureFiles.get(counter).getName().substring(0,
                                featureFiles.get(counter).getName().lastIndexOf(".")) + ".json";
                        if (startCucumberReporting) {
                            jsonResults.add(reportPath);
                        }
                        plugins[i] = "json:" + reportPath;
                    } else
                        plugins[i] = plugin;
                }
                if (startExtentReporter) {
                    List<String> pluginsList = new ArrayList<>(Arrays.asList(plugins));
                    pluginsList.add(CustomCucumberListener.class.getCanonicalName());
                    plugins = pluginsList.toArray(new String[pluginsList.size()]);
                }
                return plugins;
            }

            @Override
            public String[] name() {
                return originalAnnotation.name();
            }

            @Override
            public boolean monochrome() {
                return originalAnnotation.monochrome();
            }

            @Override
            public String[] glue() {
                return originalAnnotation.glue();
            }

            @SuppressWarnings("deprecation")
            @Override
            public String[] format() {
                return originalAnnotation.format();
            }

            @Override
            public String[] features() {
                return new String[]{featureFiles.get(counter).getAbsolutePath()};
            }

            @Override
            public boolean dryRun() {
                return originalAnnotation.dryRun();
            }
        };

        for (int i = 0; i < featureFiles.size(); i++) {
            Method method = Class.class.getDeclaredMethod("annotationData");
            method.setAccessible(true);
            Object annotationData = method.invoke(originalRunnerClass);
            Field field = annotationData.getClass().getDeclaredField("annotations");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<Class<? extends Annotation>, Annotation> annotations = (Map<Class<? extends Annotation>, Annotation>) field
                    .get(annotationData);
            annotations.put(CucumberOptions.class, newAnnotation);
            featureRunners.add(originalRunnerClass);
        }
        return this;
    }

    /**
     * @param templateRunnerClassName - FQN of Class name of the template Junit Runner class which will
     *                                specify the feature files directory location, reporting format
     *                                etc.,
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public CucumberParallelRunner configureParallelScenarioExecution(String templateRunnerClassName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, IOException {
        if (ParallelScenarioBuilder.dir.exists())
            FileUtils.cleanDirectory(ParallelScenarioBuilder.dir);
        List<Class<?>> featureRunners = new ArrayList<>();
        Class<?> originalRunnerClass = Class.forName(templateRunnerClassName);
        CucumberOptions originalAnnotation = originalRunnerClass.getAnnotation(CucumberOptions.class);
        CucumberOptions newAnnotation = new CucumberOptions() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return originalAnnotation.annotationType();
            }

            @Override
            public String[] tags() {
                return originalAnnotation.tags();
            }

            @Override
            public boolean strict() {
                return originalAnnotation.strict();
            }

            @Override
            public SnippetType snippets() {
                return originalAnnotation.snippets();
            }

            @Override
            public String[] junit() {
                return new String[0];
            }

            @Override
            public String[] plugin() {
                return new String[]{ParallelScenarioBuilder.class.getCanonicalName()};
            }

            @Override
            public String[] name() {
                return originalAnnotation.name();
            }

            @Override
            public boolean monochrome() {
                return originalAnnotation.monochrome();
            }

            @Override
            public String[] glue() {
                return originalAnnotation.glue();
            }

            @SuppressWarnings("deprecation")
            @Override
            public String[] format() {
                return originalAnnotation.format();
            }

            @Override
            public String[] features() {
                return originalAnnotation.features();
            }

            @Override
            public boolean dryRun() {
                return true;
            }
        };
        Method method = Class.class.getDeclaredMethod("annotationData");
        method.setAccessible(true);
        Object annotationData = method.invoke(originalRunnerClass);
        Field field = annotationData.getClass().getDeclaredField("annotations");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<? extends Annotation>, Annotation> annotations = (Map<Class<? extends Annotation>, Annotation>) field.get(annotationData);
        annotations.put(CucumberOptions.class, newAnnotation);
        featureRunners.add(originalRunnerClass);
        JUnitCore.runClasses(ParallelConfig.classes(), featureRunners.toArray(new Class<?>[featureRunners.size()]));
        annotations.put(CucumberOptions.class, originalAnnotation);
        return this;
    }

    /**
     * @param numberOfThreads
     * @return
     * @throws SecurityException
     * @throws IllegalArgumentException
     */
    public CucumberParallelRunner run(int numberOfThreads) throws SecurityException, IllegalArgumentException {
        results = JUnitCore.runClasses(ParallelConfig.cucumberScenarios(numberOfThreads),
                featureRunners.toArray(new Class<?>[featureRunners.size()]));
        for (Failure failure : results.getFailures()) {
            System.out.println(failure.getTrace());
            System.out.println(failure.getMessage());
        }
        return this;
    }

    /**
     * @param reportOutputDirectory
     * @param projectName
     * @return
     */
    public CucumberParallelRunner cucumberReport(String reportOutputDirectory, String projectName) {
        if (startCucumberReporting) {
            Configuration configuration = new Configuration(new File(reportOutputDirectory), projectName);
            ReportBuilder reportBuilder = new ReportBuilder(jsonResults, configuration);
            reportBuilder.generateReports();
        }
        return this;
    }
}