package tests;

import base.BaseTest;
import com.sun.net.httpserver.Authenticator;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.LocatorManager;
import utils.LoggingManager;

import java.util.List;

public class WebTestAutomation extends BaseTest {

    LoggingManager loggingManager = new LoggingManager(WebTestAutomation.class.getName());
    @SneakyThrows
    @Test (testName = "Insider Careers Page QA Job Validation Test",groups = {"Regression"}, priority = 1)
    @Parameters("url")
    public void studyCase(@Optional("https://useinsider.com") String url) {
        String isVisibleTxt = "%s is visible";
        String isNotVisibleTxt = "%s is not visible";
        String testFailedTxt = "test failed!";
        String failOpenPage = "%s did not open successfully, " + testFailedTxt;

        // Navigate to home page
        String logoKey = "insiderLogo";
        String acceptAllCookiesBtn = "acceptAllCookiesBtn";
        super.methods.navigateTo(url);
        super.methods.waitUntilElementVisible(logoKey);
        super.methods.waitUntilElementClickable(acceptAllCookiesBtn);
        super.methods.clickElement(acceptAllCookiesBtn);

        // Checks if home page is visible or not
        String containerKey = "homePageExperiencesContainer";
        boolean isLogoVisible = super.methods.isElementVisible(logoKey);
        boolean isExperiencesContainerVisible = super.methods.isElementVisible(containerKey);

        Assert.assertTrue(
                isLogoVisible && isExperiencesContainerVisible,
                String.format(failOpenPage, "Home page")
        );

        loggingManager.logInfo(String.format(isVisibleTxt, logoKey));
        loggingManager.logInfo(String.format(isVisibleTxt, containerKey));

        // Goes to Careers from the navigation bar and checks if Careers page is visible or not
        String companyMenuKey = "companyMenu";
        String careersSubMenuKey = "careersSubMenu";
        String careersHeadSection = "careersPageHeadSection";
        String locationsKey = "careersPageLocationsSlider";
        String teamsKey = "careersPageTeamsSection";
        String lifeAtInsiderKey = "careersPageLifeAtInsiderSection";

        super.methods.clickElement(companyMenuKey);
        super.methods.waitUntilElementClickable(careersSubMenuKey);
        super.methods.clickElement(careersSubMenuKey);
        super.methods.waitUntilElementVisible(careersHeadSection);

        Assert.assertTrue(
                methods.isElementVisible(careersHeadSection),
                String.format(isNotVisibleTxt + " ," + testFailedTxt, careersHeadSection)
        );

        boolean isLocationsVisible = super.methods.isElementVisible(locationsKey);
        boolean isTeamsVisible = super.methods.isElementVisible(teamsKey);
        boolean isLifeAtInsiderVisible = super.methods.isElementVisible(lifeAtInsiderKey);

        Assert.assertTrue(
                isLocationsVisible && isTeamsVisible && isLifeAtInsiderVisible,
                String.format(failOpenPage, "Careers")
        );

        loggingManager.logInfo(String.format(isVisibleTxt, locationsKey));
        loggingManager.logInfo(String.format(isVisibleTxt, teamsKey));
        loggingManager.logInfo(String.format(isVisibleTxt, lifeAtInsiderKey));

        // Navigates to https://useinsider.com/careers/quality-assurance/
        url = "https://useinsider.com/careers/quality-assurance/";
        super.methods.navigateTo(url);
        super.methods.waitUntilElementVisible(careersHeadSection);

        // Clicks to 'See all QA jobs' button
        String seeAllQAJobsBtnKey = "careersPageSeeAllQAJobsBtn";
        String locationFilterKey = "careersOpenPositionFilterLocation";
        String departmentFilterKey = "careersOpenPositionFilterDepartment";
        super.methods.clickElement(seeAllQAJobsBtnKey);
        Thread.sleep(10000);
        super.methods.waitUntilElementVisible(careersHeadSection);

        boolean isLocationFilterVisible = super.methods.isElementVisible(locationFilterKey);
        boolean isDepartmentFilterVisible = super.methods.isElementVisible(departmentFilterKey);

        Assert.assertTrue(
                isDepartmentFilterVisible && isLocationFilterVisible,
                String.format(failOpenPage, "All Open Positions for QA")
        );

        /* Checks that all jobs' Position contains 'Quality Assurance',
            Department contains 'Quality Assurance', and Location contains 'Istanbul, Turkey'*/

        String titleTxt = "title";

        String location = "Istanbul, Turkey";
        String department = "Quality Assurance";
        String locationFilterIstanbulTurkeyKey = "careersOpenPositionFilterLocationIstanbulTurkey";
        String departmentFilterQualityAssuranceKey = "careersOpenPositionFilterDepartmentQualityAssurance";
        String openPositionCardsDepartmentKey = "openPositionsCardsDepartment";

        super.methods.clickElement(locationFilterKey);
        super.methods.clickElement(locationFilterIstanbulTurkeyKey);

        super.methods.clickElement(departmentFilterKey);
        super.methods.clickElement(departmentFilterQualityAssuranceKey);

        String locationFilterValue = super.methods.getAttribute(locationFilterKey, titleTxt);
        String departmentFilterValue = super.methods.getAttribute(departmentFilterKey, titleTxt);

        boolean isLocationFilterContains = locationFilterValue.contains(location);
        boolean isDepartmentFilterContains = departmentFilterValue.contains(department);

        Assert.assertTrue(
                isLocationFilterContains,
                "Location filter does not contains " + location + ", " + testFailedTxt
        );

        Assert.assertTrue(
                isDepartmentFilterContains,
                "Department filter does not contains " + department + ", " + testFailedTxt
        );
        Thread.sleep(5000);
        List<WebElement> cards = super.methods
                .findElements(LocatorManager.getLocator(openPositionCardsDepartmentKey));


        for (WebElement element : cards) {
            Assert.assertEquals(
                    department,
                    element.getText(),
                    "Not all open positions contains " + department + ", " + testFailedTxt);
        }


        // Clicks the 'View Role' button and checks that button redirected to the Lever Application form page
        String currentWindow = driver.getWindowHandle();
        url = "jobs.lever.co/useinsider";
        super.methods.scrollToElement(cards.get(0));
        Thread.sleep(500L);
        super.methods.hover(cards.get(0));
        By viewRoleBtn = By.xpath(String.format("//div//a[contains(@href,'%s')]", url));
        super.methods.waitUntilElementClickable(cards.get(0).findElement(viewRoleBtn));
        super.methods.clickElement(cards.get(0).findElement(viewRoleBtn));

        super.methods.waitUntilNumberOfWindowsToBe(2);
        for (String windowHandle : driver.getWindowHandles()) {
            if(!currentWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        super.methods.waitUntilUrlContains(url);

        String currentUrl = super.driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains(url),
                String.format(failOpenPage, "Lever Application form page")
        );
    }
}