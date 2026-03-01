package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WelcomePage {

    private final WebDriver driver;

    // ====== CONFIG ======
    public static final String BASE_URL = "https://dumdurrust.stage.lynxbypallet.com/";
    public static final String EXPECTED_TITLE = "Dum Durrust";
    public static final String EXPECTED_RESTAURANT_NAME = "Welcome to Dum Durrust";
    public static final String DELIVERY_PAGE_URL_PART = "/home";

    // ====== LOCATORS ======
    private final By logoImg = By.xpath("//div[@class='welcome-section MuiBox-root css-0']//img");
    private final By deliveryButton = By.xpath("//button[@aria-label='Order for delivery']");
    private final By takeawayButton = By.xpath("//button[@aria-label='Order for takeaway']");
    private final By restaurantNameLabel = By.xpath("//h6[normalize-space()='Welcome to Dum Durrust']");

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(BASE_URL);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isLogoDisplayed() {
        return driver.findElement(logoImg).isDisplayed();
    }

    public boolean isDeliveryButtonVisible() {
        return driver.findElement(deliveryButton).isDisplayed();
    }

    public boolean isTakeawayButtonVisible() {
        return driver.findElement(takeawayButton).isDisplayed();
    }

    public void clickDelivery() {
        driver.findElement(deliveryButton).click();
    }
    public void clickDeliveryButton() {

        driver.findElement(deliveryButton).click();
    }

    public String getRestaurantName() {

        return driver.findElement(restaurantNameLabel).getText().trim();
    }
}
