package base;

import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class BaseApiTest implements ITest {

    private final ThreadLocal<String> testName = new ThreadLocal<>();

    public void unLoadTestName() { testName.remove(); }
    @BeforeMethod
    public void beforeMethod(Method method) {
        String methodDetails = method.getAnnotation(Test.class).description();
        String methodName = method.getName();
        testName.set(methodDetails + " - [" + methodName + "]");
    }

    @Override
    public String getTestName() {
        return testName.get();
    }


}
