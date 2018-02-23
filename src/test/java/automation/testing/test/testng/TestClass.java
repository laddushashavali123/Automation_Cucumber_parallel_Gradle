package automation.testing.test.testng;

import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

public class TestClass {

    private String testObject;
    private ExtentTest test;
    private int state=0;

    public TestClass(String testObject) {
        this.testObject = testObject;
    }

    @BeforeClass
    public void beforeTest() {
        test = TestFactory.extentReports.createTest(testObject);
    }

    @Override
    public String toString() {
        return this.testObject;
    }

    @Test(priority = 1)
    public void login() throws InterruptedException {
        test.createNode("Login").pass("Login "+testObject);
        if(testObject.equalsIgnoreCase("Test 2"))
            Thread.sleep(4000);
        else
            Thread.sleep(2000);
        test.info(testObject+" "+(++state)+" "+Thread.currentThread().getId()+" "+Thread.currentThread().getStackTrace()[1]);
    }

    @Test(priority = 2, dependsOnMethods = "login")
    public void navigateTo() throws InterruptedException {
        test.createNode("NavigateTo").pass("NavigateTo "+testObject);
        if(testObject.equalsIgnoreCase("Test 2"))
            Thread.sleep(4000);
        else
            Thread.sleep(2000);
        test.info(testObject+" "+(++state)+" "+Thread.currentThread().getId()+" "+Thread.currentThread().getStackTrace()[1]);
    }

    @DataProvider
    public Object[][] dataFeeder() {
        return new Object[][] { { "a", "b" }, { "c", "d" } };
    }

    @Test(priority = 3, dataProvider = "dataFeeder", dependsOnMethods = "navigateTo")
    public void enterSomething(String s1, String s2) throws InterruptedException { // String valA, String valB
        test.createNode("EnterSomething").pass("EnterSomething "+testObject);
//        if(testObject.equalsIgnoreCase("Test 2"))
//            assertTrue(false);
        if(testObject.equalsIgnoreCase("Test 2"))
            Thread.sleep(4000);
        else
            Thread.sleep(2000);
        test.info(testObject+" "+(++state)+" "+Thread.currentThread().getId()+" "+Thread.currentThread().getStackTrace()[1]);
    }

    @Test(priority = 4, dependsOnMethods = "enterSomething")
    public void verifySomething() throws InterruptedException {
        test.createNode("VerifySomething").pass("VerifySomething "+testObject);
        if(testObject.equalsIgnoreCase("Test 2"))
            Thread.sleep(4000);
        else
            Thread.sleep(2000);
        test.info(testObject+" "+(++state)+" "+Thread.currentThread().getId()+" "+Thread.currentThread().getStackTrace()[1]);
    }
}
