package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.DeliveryHomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeliveryHomePageTest extends BaseTest {

    private DeliveryHomePage deliveryPage;

    @BeforeMethod(alwaysRun = true)
    public void openHomePage() {
        deliveryPage = new DeliveryHomePage(driver);
        deliveryPage.open();

        ensureHomeLoaded();
    }

    private void ensureHomeLoaded() {
        if (!deliveryPage.isMenuVisible()) {

        }
    }

    @Test(priority = 0, description = "DHP_B2C_WEB_01 - Verify URL")
    public void verifyUrl() {
        String actualUrl = deliveryPage.getCurrentUrl();
        Assert.assertTrue(
                actualUrl.startsWith(DeliveryHomePage.BASE_URL),
                "URL did not start with expected base URL."
        );
    }

    @Test(priority = 1, description = "DHP_B2C_WEB_02 - Verify Logo is visible")
    public void verifyLogoVisible() {
        Assert.assertTrue(deliveryPage.isLogoDisplayed(), "Logo is not displayed.");
    }

    @Test(priority = 2, description = "DHP_B2C_WEB_03 - Verify page title")
    public void verifyTitle() {
        String title = deliveryPage.getTitle();
        Assert.assertEquals(title, DeliveryHomePage.EXPECTED_TITLE, "Page title mismatch.");
    }

    @Test(priority = 3, description = "DHP_B2C_WEB_04 - Verify current address bar is visible")
    public void verifyCurrentAddressBarVisible() {
        Assert.assertTrue(deliveryPage.isCurrentAddressBarVisible(),
                "Current address bar is not visible.");
    }

    @Test(priority = 4, description = "DHP_B2C_WEB_05 - Verify Not Serviceable banner for non-serviceable location")
    public void verifyNotServiceableBannerForNonServiceableLocation() {
        deliveryPage.searchNonServiceableLocation();
        Assert.assertTrue(deliveryPage.isNotServiceableBannerVisible(),
                "'Not serviceable' banner not displayed for non-serviceable location.");
    }

    @Test(priority = 5, description = "DHP_B2C_WEB_06 - Verify Change Address button is visible")
    public void verifyChangeAddressButtonVisible() {
        Assert.assertTrue(deliveryPage.isChangeAddressButtonVisible(),
                "'Change address' button is not visible.");
    }

    @Test(priority = 6, description = "DHP_B2C_WEB_07 - Verify search bar visible initially")
    public void verifySearchBarVisibleInitially() {
        Assert.assertTrue(deliveryPage.isSearchBarVisible(),
                "Search bar is not visible initially.");
    }

    @Test(priority = 7, description = "DHP_B2C_WEB_08 - Verify menu visible initially")
    public void verifyMenuVisibleInitially() {
        Assert.assertTrue(deliveryPage.isMenuVisible(),
                "Menu section is not visible initially.");
    }

    @Test(priority = 8, description = "DHP_B2C_WEB_09 - Verify delivery for serviceable location")
    public void verifyDeliveryForServiceableLocation() {
        deliveryPage.searchServiceableLocation();
        Assert.assertTrue(deliveryPage.isHomeBannerVisible(),
                "Serviceable area indicator not shown after entering serviceable location.");
    }

    @Test(priority = 9, description = "DHP_B2C_WEB_10 - Verify banner after entering serviceable location")
    public void verifyBannerAfterEnteringServiceableLocation() {
        deliveryPage.searchServiceableLocation();
        Assert.assertTrue(deliveryPage.isHomeBannerVisible(),
                "Home/banner not visible after entering serviceable location.");
    }

    @Test(priority = 10, description = "DHP_B2C_WEB_11 - Verify menu after entering serviceable location")
    public void verifyMenuAfterServiceableLocation() {
        deliveryPage.searchServiceableLocation();
        Assert.assertTrue(deliveryPage.isMenuVisible(),
                "Menu is not visible after entering serviceable location.");
    }

    @Test(priority = 11, description = "DHP_B2C_WEB_12 - Verify search bar after entering serviceable location")
    public void verifySearchBarAfterEnteringServiceableLocation() {
        deliveryPage.searchServiceableLocation();
        Assert.assertTrue(deliveryPage.isSearchBarVisible(),
                "Search bar not visible after entering serviceable location.");
    }

    @Test(priority = 12, description = "DHP_B2C_WEB_17 - Verify profile icon is visible")
    public void verifyProfileIconVisible() {
        Assert.assertTrue(deliveryPage.isProfileIconVisible(),
                "Profile icon is not visible.");
    }

    @Test(priority = 13, description = "DHP_B2C_WEB_18 - Verify wishlist icon is visible")
    public void verifyWishlistIconVisible() {
        Assert.assertTrue(deliveryPage.isWishlistIconVisible(),
                "Wishlist icon is not visible.");
    }

    @Test(priority = 14, description = "DHP_B2C_WEB_19-23 - Verify elements after browser refresh")
    public void verifyElementsAfterBrowserRefresh() {

        deliveryPage.searchServiceableLocation();
        deliveryPage.refresh();

        Assert.assertTrue(deliveryPage.isServiceableAreaChipVisible(),
                "Serviceable area chip not visible after refresh.");

        Assert.assertTrue(deliveryPage.isProfileIconVisible(),
                "Profile icon not visible after refresh.");

        Assert.assertTrue(deliveryPage.isWishlistIconVisible(),
                "Wishlist icon not visible after refresh.");

        Assert.assertTrue(deliveryPage.isSearchBarVisible(),
                "Search bar not visible after refresh.");
    }
}