package listeners;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestResultListeners extends BaseTest implements ITestListener {
    private final Logger loggerListener = LogManager.getLogger(TestResultListeners.class.getName());

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            failedScreenshot();
        } catch (Exception e) {
            loggerListener.error("Failed to create screenshot");
            e.printStackTrace();
        }
    }
}
