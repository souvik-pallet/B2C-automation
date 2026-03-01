//package org.example.tests;
//
//import org.example.base.BaseTest;
//import org.example.pages.DeliveryHomePage;
//import org.testng.Assert;
//import org.testng.Reporter;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//public class DeliveryHomePageTest extends BaseTest {
//
//    private DeliveryHomePage homePage;
//
//    @BeforeMethod(alwaysRun = true)
//    public void openHomePage() {
//        homePage = new DeliveryHomePage(driver);
//        Reporter.log("Opening Delivery Home Page URL", true);
//        homePage.open();
//        ensureHomeLoaded();
//    }
//
//    /**
//     * Minor guard to ensure page ready for assertions.
//     * Keeps tests more stable in case of slow loads.
//     */
//    private void ensureHomeLoaded() {
//        // waitUntilReady() is available on the page object (it uses internal waits)
//        // If you did not implement it, you can rely on isMenuVisible or other stable element
//        if (!homePage.isMenuVisible()) {
//            Reporter.log("Menu not visible immediately after open — proceeding anyway (page object waits will handle it).", true);
//        }
//    }
//
//    // DHP_B2C_WEB_01 - Verify url
//    @Test
//    public void verifyUrl() {
//        Reporter.log("DHP_B2C_WEB_01: Step 1 - Verify browser landed on correct URL", true);
//        String actualUrl = homePage.getCurrentUrl();
//        Reporter.log("Actual URL: " + actualUrl, true);
//        Assert.assertTrue(
//                actualUrl.startsWith(DeliveryHomePage.BASE_URL),
//                "URL did not start with expected base URL."
//        );
//    }
//
//    // DHP_B2C_WEB_02 - Verify Logo
//    @Test
//    public void verifyLogoVisible() {
//        Reporter.log("DHP_B2C_WEB_02: Verifying logo is visible on Home Page", true);
//        Assert.assertTrue(homePage.isLogoDisplayed(), "Logo is not displayed.");
//    }
//
//    // DHP_B2C_WEB_03 - Verify title
//    @Test
//    public void verifyTitle() {
//        Reporter.log("DHP_B2C_WEB_03: Verifying page title", true);
//        String title = homePage.getTitle();
//        Reporter.log("Page Title: " + title, true);
//        Assert.assertEquals(title, DeliveryHomePage.EXPECTED_TITLE, "Page title mismatch.");
//    }
//
//    // DHP_B2C_WEB_04 - Verify current address bar
//    @Test
//    public void verifyCurrentAddressBarVisible() {
//        Reporter.log("DHP_B2C_WEB_04: Verifying current address bar is visible", true);
//        Assert.assertTrue(homePage.isCurrentAddressBarVisible(),
//                "Current address bar is not visible.");
//    }
//
//    // DHP_B2C_WEB_05 - Verify Not serviceable Banner without entering serviceable area
//    @Test
//    public void verifyNotServiceableBannerForNonServiceableLocation() {
//        Reporter.log("DHP_B2C_WEB_05: Checking 'Not serviceable' banner visibility (no address entered)", true);
//        // Some apps show this banner by default — if yours doesn't, this assertion will reflect actual behavior.
//        Assert.assertTrue(homePage.isNotServiceableBannerVisible(),
//                "'Not serviceable' banner not displayed for non-serviceable location.");
//    }
//
//    // DHP_B2C_WEB_06 - Verify change address button is there
//    @Test
//    public void verifyChangeAddressButtonVisible() {
//        Reporter.log("DHP_B2C_WEB_06: Verifying change address button is visible", true);
//        Assert.assertTrue(homePage.isChangeAddressButtonVisible(),
//                "'Change address' button is not visible.");
//    }
//
//    // DHP_B2C_WEB_07 - Verify search bar without entering serviceable area
//    @Test
//    public void verifySearchBarVisibleInitially() {
//        Reporter.log("DHP_B2C_WEB_07: Verifying search bar is visible before any address is entered", true);
//        Assert.assertTrue(homePage.isSearchBarVisible(),
//                "Search bar is not visible initially.");
//    }
//
//    // DHP_B2C_WEB_08 - Verify menu without entering serviceable area
//    @Test
//    public void verifyMenuVisibleInitially() {
//        Reporter.log("DHP_B2C_WEB_08: Verifying menu visibility before entering serviceable area", true);
//        Assert.assertTrue(homePage.isMenuVisible(),
//                "Menu section is not visible initially (check expected behavior).");
//    }
//
//    // DHP_B2C_WEB_09 - Verify delivery location by entering serviceable location
//    @Test
//    public void verifyDeliveryForServiceableLocation() {
//        Reporter.log("DHP_B2C_WEB_09: Entering serviceable location", true);
//        homePage.searchServiceableLocation();
//        Reporter.log("Checking serviceable area chip visibility", true);
//        Assert.assertTrue(homePage.isServiceableAreaChipVisible(),
//                "Serviceable area indicator not shown after entering serviceable location.");
//    }
//
//    // DHP_B2C_WEB_10 - Verify Banner after entering serviceible location
//    @Test
//    public void verifyBannerAfterEnteringServiceableLocation() {
//        Reporter.log("DHP_B2C_WEB_10: Entering serviceable location and checking banner", true);
//        homePage.searchServiceableLocation();
//        Assert.assertTrue(homePage.isHomeBannerVisible(),
//                "Home/banner not visible after entering serviceable location (expected).");
//    }
//
//    // DHP_B2C_WEB_11 - Verify menu after entering serviceible delivery location
//    @Test
//    public void verifyMenuAfterServiceableLocation() {
//        Reporter.log("DHP_B2C_WEB_11: Entering serviceable location and verifying menu", true);
//        homePage.searchServiceableLocation();
//        Assert.assertTrue(homePage.isMenuVisible(),
//                "Menu is not visible after entering serviceable location.");
//    }
//
//    // DHP_B2C_WEB_12 - Verify search bar after entering delivery location
//    @Test
//    public void verifySearchBarAfterEnteringServiceableLocation() {
//        Reporter.log("DHP_B2C_WEB_12: Entering serviceable location and verifying search bar", true);
//        homePage.searchServiceableLocation();
//        Assert.assertTrue(homePage.isSearchBarVisible(),
//                "Search bar not visible after entering serviceable location.");
//    }
//
//    // DHP_B2C_WEB_17 - Verify profile icon
//    @Test
//    public void verifyProfileIconVisible() {
//        Reporter.log("DHP_B2C_WEB_17: Verifying profile icon is visible", true);
//        Assert.assertTrue(homePage.isProfileIconVisible(),
//                "Profile icon is not visible.");
//    }
//
//    // DHP_B2C_WEB_18 - Verify wishlist icon
//    @Test
//    public void verifyWishlistIconVisible() {
//        Reporter.log("DHP_B2C_WEB_18: Verifying wishlist icon is visible", true);
//        Assert.assertTrue(homePage.isWishlistIconVisible(),
//                "Wishlist icon is not visible.");
//    }
//
//    // DHP_B2C_WEB_19 / 20 / 21 / 22 / 23 – refresh scenarios
//    @Test
//    public void verifyElementsAfterBrowserRefresh() {
//        Reporter.log("DHP_B2C_WEB_19-23: Entering serviceable location, refreshing, and verifying elements", true);
//
//        homePage.searchServiceableLocation();
//        Reporter.log("Refreshing page...", true);
//        homePage.refresh();
//
//        Reporter.log("Checking serviceable area chip at top", true);
//        Assert.assertTrue(homePage.isServiceableAreaChipVisible(),
//                "Serviceable area chip not visible after refresh.");
//
//        Reporter.log("Checking profile icon after refresh", true);
//        Assert.assertTrue(homePage.isProfileIconVisible(),
//                "Profile icon not visible after refresh.");
//
//        Reporter.log("Checking wishlist icon after refresh", true);
//        Assert.assertTrue(homePage.isWishlistIconVisible(),
//                "Wishlist icon not visible after refresh.");
//
//        Reporter.log("Checking search bar after refresh", true);
//        Assert.assertTrue(homePage.isSearchBarVisible(),
//                "Search bar not visible after refresh.");
//    }
//}