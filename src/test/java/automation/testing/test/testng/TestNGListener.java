package automation.testing.test.testng;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.List;

public class TestNGListener implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        for (IMethodInstance method :
                methods) {
            System.out.println("===>"+method.getInstance());
            System.out.println("===>"+method.getMethod());
        }
        return methods;
    }
}
