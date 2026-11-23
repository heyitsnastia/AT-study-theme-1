package ru.study.topic6.selenide;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.study.topic6.support.UiConfig;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.confirm;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;

@Tag("topic6-selenide")
@DisplayName("Тема 6. Задача 2 — Selenide")
class SelenideLectureTest extends SelenideTestSupport {
    @Test
    @DisplayName("2.1. Добавленный через админку товар отображается на витрине")
    void createdProductAppearsOnStorefront() {
        String name = products.newName();
        login(UiConfig.USER, UiConfig.PASSWORD);
        createProductInAdmin(name, "75");
        notificationShouldBe("Товар успешно добавлен!");
        $("a[href='/']").click();

        String card = ".product-card[data-name='" + name + "']";
        $(card).shouldBe(visible, attribute("data-price", "75"));
        $(card + " h4").shouldBe(exactText(name));
    }

    @Test
    @DisplayName("2.2. Добавленный товар отображается в корзине")
    void addedProductAppearsInCart() {
        var product = products.create("80");
        open("/");
        addToCart(product, 1);
        $("#open-cart-btn").click();

        cartItemShouldBe(product, 1);
        $("#cart-count").shouldBe(exactText("1"));
        $("#total-price").shouldBe(exactText("80"));
    }

    @Test
    @DisplayName("2.3. Неверные логин и пароль не дают войти в админку")
    void invalidCredentialsAreRejected() {
        login("invalid-" + products.newName(), "incorrect-password");

        // Spring Security локализует текст ошибки по языку браузера.
        $(".alert-danger").shouldBe(visible, not(empty));
        open("/admin");
        $("#username").shouldBe(visible);
        $("#n-name").shouldBe(hidden);
    }

    @Test
    @DisplayName("2.4. Товары и их количество сохраняются после обновления страницы")
    void cartSurvivesPageRefresh() {
        var product = products.create("60");
        open("/");
        addToCart(product, 2);
        $("#open-cart-btn").click();
        cartItemShouldBe(product, 2);
        $("#total-price").shouldBe(exactText("120"));

        refresh();
        $("#open-cart-btn").click();

        cartItemShouldBe(product, 2);
        $("#cart-count").shouldBe(exactText("2"));
        $("#total-price").shouldBe(exactText("120"));
    }

    @Test
    @DisplayName("2.5. Заказ дороже 300 рублей вызывает JS Alert")
    void orderOverLimitShowsAlert() {
        var product = products.create("151");
        open("/");
        addToCart(product, 2);
        $("#open-cart-btn").click();
        cartItemShouldBe(product, 2);
        $("#total-price").shouldBe(exactText("302"));

        $("#makeOrder").click();

        // Нативный JS Alert не является DOM-элементом. Selenide сам ждёт диалог,
        // проверяет его текст и нажимает OK; отсутствие/иной текст провалят тест.
        confirm("[SmartShop]: Денег не хватает! Сумма 302 ₽ превышает лимит 300 ₽.");
        $("#cartModal").shouldBe(visible);
        cartItemShouldBe(product, 2);
        $("#total-price").shouldBe(exactText("302"));
    }
}
