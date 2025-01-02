package base;


import driver.Browsers;
import driver.DriverManager;
import methods.WebTestMethods;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.LocatorManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class BaseTest extends LocatorManager {
    protected WebDriver driver;
    public static final long driverWait = 30;
    private final long driverPageLoadWait = 120;
    protected WebTestMethods methods;

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        final String browser = context.getCurrentXmlTest().getParameter("browser") != null ?
                context.getCurrentXmlTest().getParameter("browser") : "chrome";
        DriverManager.setDriver(Browsers.getLocalDriver(browser));
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(driverWait));
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(driverPageLoadWait));
        driver = DriverManager.getDriver();
        methods = new WebTestMethods(driver);
    }

    @AfterSuite
    public void afterSuite() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
        }
    }

    protected void failedScreenshot() {
        String rootDir = System.getProperty("user.dir");
        File srcFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        Date date = new Date();
        String timeStamp = date.toString().replace(":", "_").replace(" ", "_");

        try {
            FileUtils.copyFile(srcFile, new File(rootDir + "/screenshots/" + timeStamp + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
