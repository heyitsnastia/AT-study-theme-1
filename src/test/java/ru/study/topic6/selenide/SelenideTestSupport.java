package ru.study.topic6.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import ru.study.topic6.support.ProductFixtures;
import ru.study.topic6.support.UiConfig;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@Tag("ui")
public abstract class SelenideTestSupport {
    protected final ProductFixtures products = new ProductFixtures();

    @BeforeEach
    void openBrowser() {
        Configuration.baseUrl = UiConfig.BASE_URL;
        Configuration.browser = "chrome";
        Configuration.headless = UiConfig.HEADLESS;
        Configuration.browserSize = "1440x1000";
        Configuration.timeout = UiConfig.TIMEOUT_SECONDS * 1000;
        Configuration.pageLoadTimeout = 30_000;
        open("/");
        $("#main-title").shouldBe(visible);
    }

    @AfterEach
    void deleteTestDataAndCloseBrowser() {
        try {
            products.deleteCreatedProducts();
        } finally {
            closeWebDriver();
        }
    }

    protected void login(String username, String password) {
        open("/login");
        $("#username").setValue(username);
        $("#password").setValue(password);
        $("button[type='submit']").click();
    }

    protected void createProductInAdmin(String name, String price) {
        $("#n-name").setValue(name);
        $("#n-price").setValue(price);
        $("#add-btn").click();
    }

    protected void notificationShouldBe(String message) {
        $$("#toast-container .toast").findBy(exactText(message)).shouldBe(visible);
    }

    protected void addToCart(ProductFixtures.Product product, int quantity) {
        $("#q-" + product.id()).setValue(Integer.toString(quantity));
        $("#card-" + product.id() + " [data-action='add-to-cart']").click();
    }

    protected void cartItemShouldBe(ProductFixtures.Product product, int quantity) {
        String item = "#cart-item-" + product.id();
        $(item).shouldBe(visible);
        $(item + " b").shouldBe(exactText(product.name()));
        $(item + " .qty-controls span").shouldBe(exactText(Integer.toString(quantity)));
    }
}
