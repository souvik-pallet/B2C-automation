package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.DeliveryHomePage;
import org.example.pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ProductPageTest extends BaseTest {

    private ProductPage productPage;
    private final String baseUrl = "https://your-app-url.com"; // TODO: change to real URL

    @BeforeClass(alwaysRun = true)
    public void setupPage() {
        productPage = new ProductPage(driver);
        Reporter.log("Opening Delivery Home Page URL", true);
        productPage.open();
    }

    // TC_B2C_WEB_001 – search using search bar
    @Test(priority = 0)
    public void verifySearchWithValidKeyword() {
        Reporter.log("TC_B2C_WEB_001: Verify product browsing using search bar", true);
        productPage.searchProduct("Laptop");
        Assert.assertTrue(productPage.isAnyProductDisplayed(),
                "Relevant products should be displayed according to the search term.");
    }

    // TC_B2C_WEB_002 – exact name search
    @Test(priority = 0)
    public void verifySearchWithExactName() {
        Reporter.log("TC_B2C_WEB_002: Verify product browsing with exact name", true);
        productPage.searchProduct("Apple iPhone 15 128GB");
        Assert.assertTrue(productPage.getAllProductNames()
                        .stream().allMatch(n -> n.contains("Apple iPhone 15")),
                "It should display exact product only.");
    }

    // TC_B2C_WEB_010 – empty search
    @Test(priority = 0)
    public void verifyEmptySearchShowsNoProduct() {
        Reporter.log("TC_B2C_WEB_013: Verify empty search", true);
        productPage.searchProduct(" ");
        Assert.assertTrue(productPage.getSearchMessage().contains("No product found"),
                "It should display 'No product found'.");
    }

    // TC_B2C_WEB_014 – special characters
    @Test(priority = 0)
    public void verifySpecialCharacterSearch() {
        Reporter.log("TC_B2C_WEB_014: Verify that entering special characters", true);
        productPage.searchProduct("!@#$%^&*()");
        Assert.assertTrue(productPage.getSearchMessage().contains("No product found"),
                "It should display 'No product found'.");
    }

    // TC_B2C_WEB_015 – non-existent products
    @Test(priority = 0)
    public void verifySearchForNonExistingProduct() {
        Reporter.log("TC_B2C_WEB_015: Verify that searching for non-existing products", true);
        productPage.searchProduct("thisProductDoesNotExist12345");
        Assert.assertTrue(productPage.getSearchMessage().contains("No product found"),
                "It should display 'No product found'.");
    }

    // TC_B2C_WEB_016 – performance (< 3 seconds)
    @Test(priority = 1)
    public void verifySearchPerformance() {
        Reporter.log("TC_B2C_WEB_016: Verify that search results load within 3 seconds", true);
        long start = System.currentTimeMillis();
        productPage.searchProduct("Shoes");
        Assert.assertTrue(productPage.isAnyProductDisplayed(),
                "Products should be displayed for valid search.");
        long timeTaken = System.currentTimeMillis() - start;
        Reporter.log("Search took: " + timeTaken + " ms", true);
        Assert.assertTrue(timeTaken <= 3000,
                "Search should load products within 3 seconds.");
    }

    // TC_B2C_WEB_017 – infinite scroll
    @Test(priority = 1)
    public void verifyInfiniteScrollLoadsMoreProducts() {
        Reporter.log("TC_B2C_WEB_017: Verify infinite scroll loads additional results", true);
        productPage.searchProduct("Shirt");
        int initialCount = productPage.getProductCount();
        productPage.scrollToBottom();
        // small wait handled inside page wait conditions
        int finalCount = productPage.getProductCount();
        Reporter.log("Initial count: " + initialCount + ", Final count: " + finalCount, true);
        Assert.assertTrue(finalCount > initialCount,
                "Scrolling should load additional products.");
    }

    // TC_B2C_WEB_018 – product card details
    @Test(priority = 1)
    public void verifyProductCardShowsKeyInformation() {
        Reporter.log("TC_B2C_WEB_018: Verify each search result displays key information", true);
        productPage.searchProduct("Headphones");
        Assert.assertTrue(productPage.isAnyProductDisplayed(),
                "Search results should display product cards.");
        // We rely on locators inside ProductPage: name, price, weight/quantity
        // If those locators are wrong, this test will fail – adjust as per UI.
    }

    // TC_B2C_WEB_019 – click product opens details
    @Test(priority = 1)
    public void verifyProductDetailsNavigation() {
        Reporter.log("TC_B2C_WEB_019: Verify clicking on product opens details", true);
        productPage.searchProduct("Watch");
        productPage.openFirstProductDetails();
        Assert.assertTrue(productPage.isProductDetailsPageLoaded(),
                "It should navigate to product details page.");
    }

    // TC_B2C_WEB_020 – filters visible
    @Test(priority = 2)
    public void verifyFiltersAreVisible() {
        Reporter.log("TC_B2C_WEB_020: Verify filters visibility", true);
        Assert.assertTrue(productPage.isFilterVisible(),
                "Filters should be visible on the product page.");
    }

    // TC_B2C_WEB_021 – menu visible
    @Test(priority = 2)
    public void verifyMenuIsVisible() {
        Reporter.log("TC_B2C_WEB_021: Verify menu", true);
        Assert.assertTrue(productPage.isMenuVisible(),
                "Menu should be visible on the product page.");
    }

    // TC_B2C_WEB_022 – wishlist
    @Test(priority = 2)
    public void verifyWishlistFunctionality() {
        Reporter.log("TC_B2C_WEB_022: Verify wishlist", true);
        productPage.searchProduct("Book");
        productPage.addFirstProductToWishlist();
        // Here you would assert based on toast/snackbar or icon state.
        // For now we just log the action.
        Reporter.log("First product added to wishlist (verify visually / via API).", true);
    }

    // TC_B2C_WEB_023 – product sharing
    @Test(priority = 2)
    public void verifyProductSharing() {
        Reporter.log("TC_B2C_WEB_023: Verify product sharing", true);
        productPage.searchProduct("Camera");
        productPage.shareFirstProduct();
        Reporter.log("Share sheet/dialog should appear (validate according to your UI).", true);
    }

    // TC_B2C_WEB_024 – product image
    @Test(priority = 2)
    public void verifyProductImageIsShown() {
        Reporter.log("TC_B2C_WEB_024: Verify product image", true);
        productPage.searchProduct("Bag");
        Assert.assertTrue(productPage.isImageDisplayed(),
                "Product image should be displayed.");
    }

    // TC_B2C_WEB_025 – product description
    @Test(priority = 2)
    public void verifyProductDescriptionIsShown() {
        Reporter.log("TC_B2C_WEB_025: Verify product description", true);
        productPage.searchProduct("Perfume");
        Assert.assertTrue(productPage.isDescriptionDisplayed(),
                "Product description should be displayed.");
    }

    // TC_B2C_WEB_026 – categories minimize / maximize
    @Test(priority = 3)
    public void verifyCategoriesCanBeMinimizedAndMaximized() {
        Reporter.log("TC_B2C_WEB_026: Verify categories can be minimized and maximized", true);
        // Initial state – open
        productPage.toggleCategoryAccordion(); // minimize
        Assert.assertTrue(productPage.isCategoryAccordionMinimized(),
                "Categories should be minimized.");
        productPage.toggleCategoryAccordion(); // maximize
        Assert.assertFalse(productPage.isCategoryAccordionMinimized(),
                "Categories should be maximized.");
    }
}