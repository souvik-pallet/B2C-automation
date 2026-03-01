package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.pages.WelcomePage.BASE_URL;


public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ---------- Top search / menu ----------

    // TC_B2C_WEB_001, 002, 003, 010, 014, 015, 016
    @FindBy(id = "search-input")
    private WebElement searchInput;

    @FindBy(css = "button[data-testid='searchButton']")
    private WebElement searchButton;

    // TC_B2C_WEB_009 (suggestions)
    @FindBy(css = "ul.search-suggestions li")
    private List<WebElement> suggestionItems;

    // generic message area – “No product found”, “New Products coming soon” etc.
    @FindBy(css = "[data-testid='search-message']")
    private WebElement searchMessage;

    // ---------- Product result cards ----------

    // List container for products
    @FindBy(css = "[data-testid='product-card']")
    private List<WebElement> productCards;

    // Any single product card (for click / details test)
    @FindBy(css = "[data-testid='product-card']:first-child")
    private WebElement firstProductCard;

    // inside each product card – name, price, weight/qty etc.
    private By productNameLocator = By.cssSelector("[data-testid='product-name']");
    private By productPriceLocator = By.cssSelector("[data-testid='product-price']");
    private By productWeightLocator = By.cssSelector("[data-testid='product-weight']");

    // ---------- Category & Brand filters ----------

    // TC_B2C_WEB_005, 011, 026
    @FindBy(css = "[data-testid='category-filter']")
    private WebElement categoryFilter;

    @FindBy(css = "[data-testid='brand-filter']")
    private WebElement brandFilter;

    // Category accordion (minimize / maximize)
    @FindBy(css = "[data-testid='category-accordion-toggle']")
    private WebElement categoryAccordionToggle;

    // ---------- Infinite scroll / layout ----------

    @FindBy(css = "body")
    private WebElement pageBody;

    // ---------- Filters / wishlist / sharing / image / description ----------

    @FindBy(css = "[data-testid='filter-button']")
    private WebElement filterButton;

    @FindBy(css = "[data-testid='wishlist-button']")
    private WebElement wishlistButton;

    @FindBy(css = "[data-testid='share-button']")
    private WebElement shareButton;

    @FindBy(css = "[data-testid='product-image']")
    private WebElement productImage;

    @FindBy(css = "[data-testid='product-description']")
    private WebElement productDescription;

    @FindBy(css = "[data-testid='main-menu']")
    private WebElement mainMenu;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ---------- Basic actions ----------

    public void open() { driver.get(BASE_URL); }

    public void enterSearchText(String text) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(text);
    }

    public void clickSearch() {
        searchButton.click();
    }

    public void searchProduct(String text) {
        enterSearchText(text);
        clickSearch();
    }

    public String getSearchMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(searchMessage)).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public List<String> getAllProductNames() {
        wait.until(d -> !productCards.isEmpty());
        return productCards.stream()
                .map(e -> e.findElement(productNameLocator).getText())
                .collect(Collectors.toList());
    }

    public boolean isAnyProductDisplayed() {
        try {
            wait.until(d -> !productCards.isEmpty());
            return productCards.size() > 0;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ---------- Suggestion search (TC_B2C_WEB_009) ----------

    public void selectSuggestionByIndex(int index) {
        wait.until(d -> !suggestionItems.isEmpty());
        if (index >= 0 && index < suggestionItems.size()) {
            suggestionItems.get(index).click();
        }
    }

    // ---------- Category / Brand (TC_B2C_WEB_004,005,011,012) ----------

    public void selectCategory(String categoryName) {
        // Example: a dropdown – adapt as needed
        categoryFilter.click();
        WebElement option = driver.findElement(By.xpath(
                "//div[@data-testid='category-option' and text()='" + categoryName + "']"));
        option.click();
    }

    public void selectBrand(String brandName) {
        brandFilter.click();
        WebElement option = driver.findElement(By.xpath(
                "//div[@data-testid='brand-option' and text()='" + brandName + "']"));
        option.click();
    }

    public boolean isCategoryAccordionMinimized() {
        return categoryFilter.getAttribute("aria-expanded").equals("false");
    }

    public void toggleCategoryAccordion() {
        categoryAccordionToggle.click();
    }

    // ---------- Infinite scroll (TC_B2C_WEB_017) ----------

    public int getProductCount() {
        return productCards.size();
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // ---------- Filters / menu / wishlist / sharing etc. ----------

    public boolean isFilterVisible() {
        try {
            return filterButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isMenuVisible() {
        try {
            return mainMenu.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void addFirstProductToWishlist() {
        wait.until(ExpectedConditions.visibilityOf(firstProductCard));
        firstProductCard.findElement(By.cssSelector("[data-testid='wishlist-icon']")).click();
    }

    public void shareFirstProduct() {
        wait.until(ExpectedConditions.visibilityOf(firstProductCard));
        firstProductCard.findElement(By.cssSelector("[data-testid='share-icon']")).click();
    }

    public void openFirstProductDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(firstProductCard)).click();
    }

    public boolean isProductDetailsPageLoaded() {
        // For example check URL or some product details element
        return wait.until(ExpectedConditions.urlContains("/product/"));
    }

    public boolean isImageDisplayed() {
        try {
            return productImage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isDescriptionDisplayed() {
        try {
            return productDescription.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}