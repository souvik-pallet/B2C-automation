package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.WelcomePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class WelcomePageTests extends BaseTest {

    private WelcomePage welcomePage;

    @BeforeMethod(alwaysRun = true)
    public void setupPage() {
        welcomePage = new WelcomePage(driver);
        welcomePage.open();
    }

    // WP_B2C_WEB_001 - Verify website url
    @Test(description = "WP_B2C_WEB_001 - Verify website URL", priority = 1)
    public void verifyWebsiteUrl() {
        String actualUrl = welcomePage.getCurrentUrl();
        Assert.assertTrue(
                actualUrl.startsWith(WelcomePage.BASE_URL),
                "Expected URL to start with: " + WelcomePage.BASE_URL + " but was: " + actualUrl
        );
    }

    // WP_B2C_WEB_002 - Verify website title
    @Test(description = "WP_B2C_WEB_002 - Verify website title", priority = 2)
    public void verifyWebsiteTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> !driver.getTitle().isEmpty());
        String actualTitle = welcomePage.getTitle();
        Assert.assertEquals(
                actualTitle,
                WelcomePage.EXPECTED_TITLE,
                "Website title is incorrect."
        );
    }

    // WP_B2C_WEB_003 - Verify Logo is correct (displayed)
    @Test(description = "WP_B2C_WEB_003 - Verify logo is displayed", priority = 3)
    public void verifyLogoIsDisplayed() {
        Assert.assertTrue(
                welcomePage.isLogoDisplayed(),
                "Logo is not displayed on welcome page."
        );
    }

    // WP_B2C_WEB_004 - Verify Delivery button is visible
    @Test(description = "WP_B2C_WEB_004 - Verify Delivery button is visible", priority = 4)
    public void verifyDeliveryButtonVisible() {
        Assert.assertTrue(
                welcomePage.isDeliveryButtonVisible(),
                "Delivery button is not visible on welcome page."
        );
    }

    // WP_B2C_WEB_005 - Verify Takeaway button is visible
    @Test(description = "WP_B2C_WEB_005 - Verify Takeaway button is visible", priority = 5)
    public void verifyTakeawayButtonVisible() {
        Assert.assertTrue(
                welcomePage.isTakeawayButtonVisible(),
                "Takeaway button is not visible on welcome page."
        );
    }

    // WP_B2C_WEB_006 - Verify Delivery button is clickable
    @Test(description = "WP_B2C_WEB_006 - Verify Delivery button is clickable", priority = 6)
    public void verifyDeliveryButtonClickable() {
        try {
            welcomePage.clickDeliveryButton();
            Assert.assertTrue(true, "Delivery button clicked successfully.");
        } catch (Exception e) {
            Assert.fail("Delivery button is not clickable. Exception: " + e.getMessage());
        }
    }

    // WP_B2C_WEB_007 - Verify clicking Delivery navigates to respective page
    @Test(description = "WP_B2C_WEB_007 - Verify clicking Delivery navigates to delivery page",
            priority = 7)
    public void verifyClickingDeliveryNavigatesCorrectly() {
        welcomePage.clickDeliveryButton();
        String urlAfterClick = welcomePage.getCurrentUrl();
        Assert.assertTrue(
                urlAfterClick.contains(WelcomePage.DELIVERY_PAGE_URL_PART),
                "After clicking Delivery, expected URL to contain '"
                        + WelcomePage.DELIVERY_PAGE_URL_PART + "' but was: " + urlAfterClick
        );
    }

    // WP_B2C_WEB_008 - Verify restaurant name is correct
    @Test(description = "WP_B2C_WEB_008 - Verify restaurant name is correct", priority = 8)
    public void verifyRestaurantName() {
        String actualName = welcomePage.getRestaurantName();
        Assert.assertEquals(
                actualName,
                WelcomePage.EXPECTED_RESTAURANT_NAME,
                "Restaurant name on welcome page is incorrect."
        );
    }
}
