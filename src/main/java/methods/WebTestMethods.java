package methods;


import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import utils.LocatorManager;
import utils.LoggingManager;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class WebTestMethods {
    private WebDriver driver;
    private Wait<WebDriver> wait;
    private final LoggingManager loggingManager = new LoggingManager(WebTestMethods.class.getName());

    public WebTestMethods(WebDriver driver) {
        this.driver = driver;
        wait = new FluentWait<>(this.driver)
                .withTimeout(Duration.ofSeconds(BaseTest.driverWait))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);
    }
    
    public WebElement findElement(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }

    public List<WebElement> findElements(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator);
    }

    public void waitUntilElementVisible(String key) {
        By by = LocatorManager.getLocator(key);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        loggingManager.logInfo(key + " is visible");
    }

    public void waitUntilElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        loggingManager.logInfo(element + " is visible");
    }

    public void waitUntilElementClickable(String key) {
        By by = LocatorManager.getLocator(key);
        wait.until(ExpectedConditions.elementToBeClickable(by));
        loggingManager.logInfo(key + " is clickable");
    }

    public void waitUntilElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        loggingManager.logInfo(element + " is clickable");
    }

    public void waitUntilUrlContains(String str) {
        wait.until(ExpectedConditions.urlContains(str));
        loggingManager.logInfo("Current url contains " + str);
    }

    public void waitUntilUrlToBe(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
        loggingManager.logInfo("Current url is " + url);
    }

    public void waitUntilNumberOfWindowsToBe(int num) {
        wait.until(ExpectedConditions.numberOfWindowsToBe(num));
        loggingManager.logInfo("Current active tabs/windows count is " + driver.getWindowHandles().size());
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
        loggingManager.logInfo("Navigated to " + url);
    }

    public void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        loggingManager.logInfo("Clicked to " + element);
    }

    public void clickElement(String key) {
        By by = LocatorManager.getLocator(key);
        wait.until(ExpectedConditions.elementToBeClickable(by));
        findElement(by).click();
        loggingManager.logInfo("Clicked to " + key);
    }

    public boolean isElementVisible(String key) {
        By by = LocatorManager.getLocator(key);
        scrollToElement(key);
        return findElement(by).isDisplayed();
    }

    public void scrollToElement(WebElement element) {
        String intoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(intoMiddle, element);
        loggingManager.logInfo("Scrolled to " + element);
    }

    public void scrollToElement(String key) {
        By by = LocatorManager.getLocator(key);
        WebElement element = findElement(by);
        String intoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(intoMiddle, element);
        loggingManager.logInfo("Scrolled to " + key);
    }

    public String getText(String key) {
        By by = LocatorManager.getLocator(key);
        return findElement(by).getText();
    }

    public String getAttribute(String key, String attribute) {
        By by = LocatorManager.getLocator(key);
        return findElement(by).getAttribute(attribute);
    }

    public void selectByText(String key, String str) {
        By by = LocatorManager.getLocator(key);
        Select select = new Select(findElement(by));
        select.selectByVisibleText(str);
        loggingManager.logInfo(str + " selected from " + key + " dropdown list");
    }

    public void hover(String key) {
        By by = LocatorManager.getLocator(key);
        Actions actions = new Actions(driver);
        actions.moveToElement(findElement(by)).perform();
        loggingManager.logInfo("Mouse hovered on " + key);
    }

    public void hover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        loggingManager.logInfo("Mouse hovered on " + element);
    }

}
