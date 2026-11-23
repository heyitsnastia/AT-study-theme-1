package ru.study.topic6.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.study.topic6.support.ProductFixtures;
import ru.study.topic6.support.UiConfig;

import java.time.Duration;

@Tag("ui")
@Tag("topic6-selenium")
abstract class SeleniumTestSupport {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final ProductFixtures products = new ProductFixtures();

    @BeforeEach
    void openBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,1000");
        if (UiConfig.HEADLESS) {
            options.addArguments("--headless=new");
        }
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, Duration.ofSeconds(UiConfig.TIMEOUT_SECONDS));
    }

    @AfterEach
    void deleteTestDataAndCloseBrowser() {
        try {
            products.deleteCreatedProducts();
        } finally {
            closeBrowser();
        }
    }

    void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected WebElement visible(String selector) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
    }

    protected void click(String selector) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector))).click();
    }

    protected void login(String username, String password) {
        driver.get(UiConfig.BASE_URL + "/login");
        visible("#username").sendKeys(username);
        visible("#password").sendKeys(password);
        click("button[type='submit']");
    }

    protected void addToCart(ProductFixtures.Product product, int quantity) {
        driver.get(UiConfig.BASE_URL + "/");
        WebElement input = visible("#q-" + product.id());
        input.clear();
        input.sendKeys(Integer.toString(quantity));
        click("#card-" + product.id() + " [data-action='add-to-cart']");
        wait.until(ExpectedConditions.textToBe(By.id("cart-count"), Integer.toString(quantity)));
        click("#open-cart-btn");
    }
}
