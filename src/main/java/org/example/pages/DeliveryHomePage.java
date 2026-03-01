package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class DeliveryHomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ====== CONFIG ======
    public static final String BASE_URL = "https://dumdurrust.stage.lynxbypallet.com/";
    public static final String EXPECTED_TITLE = "Dum Durrust";
    public static final String SERVICEABLE_PIN = "560037";
    public static final String NON_SERVICEABLE_PIN = "560090";

    // ====== TIMEOUTS ======
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration SHORT_TIMEOUT = Duration.ofSeconds(3);

    // ====== LOCATORS ======
    private final By deliveryButton = By.xpath("//button[@aria-label='Order for delivery']");
    private final By logoImg                 = By.xpath("(//img)[1]");
    private final By currentAddressBar       = By.xpath("//div[contains(@class,'header-top-grocery')]");
    private final By changeAddressButton     = By.xpath("(//button)[3]");
    private final By addressBar              = By.xpath("//div[@class='MuiBox-root css-1hcy75n']");
    private final By searchBarInput          = By.xpath("//input");
    private final By useCurrentLocationButton= By.xpath("//button");
    private final By searchSubmitButton      = By.xpath("//button[contains(text(),'Confirm')]");
    private final By notServiceableBanner    = By.xpath("//img[contains(@class,'not-serviceable-img')]");
    private final By homeBanner              = By.xpath("//img[contains(@class,'home_banner_img')]");
    private final By menuSection             = By.xpath("//span[text()='Menu']/..");
    private final By categoriesSection       = By.xpath("//h6[text()='Beverages']");
    private final By profileIcon             = By.xpath("(//button)[2]");
    private final By wishlistIcon            = By.xpath("(//button)[1]");
    private final By serviceableAreaChip     = By.cssSelector(".serviceable-area-chip");

    public DeliveryHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    // ====== NAVIGATION ======
    public void open() {
        driver.get(BASE_URL);
        pageNavigation();
    }

    public void refresh() {
        driver.navigate().refresh();
        waitUntilReady();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    private void waitUntilReady() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(menuSection));
        } catch (TimeoutException ignore) {
        }
    }

    // ====== HELPERS ======
    private Optional<WebElement> findIfPresent(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, SHORT_TIMEOUT);
            WebElement el = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return Optional.ofNullable(el);
        } catch (TimeoutException e) {
            return Optional.empty();
        }
    }

    private boolean isVisible(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, SHORT_TIMEOUT);
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ====== BASIC VISIBILITY QUERIES ======
    public boolean isLogoDisplayed() {
        return isVisible(logoImg);
    }

    public boolean isCurrentAddressBarVisible() {
        return isVisible(addressBar);
    }

    public boolean isChangeAddressButtonVisible() {
        return isVisible(changeAddressButton);
    }

    public boolean isSearchBarVisible() {
        return isVisible(searchBarInput);
    }

    public boolean isMenuVisible() {
        return isVisible(menuSection);
    }

    public boolean isCategoriesVisible() {
        return isVisible(categoriesSection);
    }

    public boolean isProfileIconVisible() {
        return isVisible(profileIcon);
    }

    public boolean isWishlistIconVisible() {
        return isVisible(wishlistIcon);
    }

    public boolean isNotServiceableBannerVisible() {
        return isVisible(notServiceableBanner);
    }

    public boolean isServiceableAreaChipVisible() {
        return isVisible(addressBar);
    }

    // ====== ACTIONS ON ADDRESS / SEARCH ======
    /**
     * Enter a location (PIN or text) and confirms the selection.
     */
    public void pageNavigation() {
        wait.until(ExpectedConditions.elementToBeClickable(deliveryButton)).click();
    }

    public void enterLocation(String location) {
        // open change address modal
        wait.until(ExpectedConditions.elementToBeClickable(addressBar)).click();

        // attempt to click 'use current location' if that's the intended flow
        // (This locator //button is ambiguous; confirm selector to avoid accidental clicks)
        wait.until(ExpectedConditions.elementToBeClickable(useCurrentLocationButton)).click();

        // wait for search input, type and wait for autocomplete results
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(searchBarInput));
        searchInput.click();
        searchInput.sendKeys(location);

        // wait for suggestions to appear and click the first suggestion
        By firstSuggestion = By.xpath("(//div[contains(@class,'pac-item') or contains(@class,'MuiAutocomplete-option')])[1]");
        wait.until(ExpectedConditions.elementToBeClickable(firstSuggestion)).click();

        // click confirm (submit) button
        wait.until(ExpectedConditions.elementToBeClickable(searchSubmitButton)).click();
        wait.until(ExpectedConditions.urlContains("home"));
    }


    public boolean checkBanner(By bannerLocator) {
        return isVisible(bannerLocator);
    }

    // convenience wrappers for your common flows:
    public void searchNonServiceableLocation() {
        enterLocation(NON_SERVICEABLE_PIN);
    }

    public void searchServiceableLocation() {
        enterLocation(SERVICEABLE_PIN);
    }

    public boolean isHomeBannerVisible() {
        return checkBanner(homeBanner);
    }
}