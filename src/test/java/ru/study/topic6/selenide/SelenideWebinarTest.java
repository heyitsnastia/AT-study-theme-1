package ru.study.topic6.selenide;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.study.topic6.support.UiConfig;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Tag("topic6-webinar")
@DisplayName("Тема 6. Задача 3 — вебинар")
class SelenideWebinarTest extends SelenideTestSupport {
    @Test
    @DisplayName("3.1. Покупка трёх единиц товара на сумму 300 рублей")
    void threeItemsCanBePaidWithinLimit() {
        var product = products.create("100");
        open("/");
        addToCart(product, 3);
        $("#open-cart-btn").click();
        cartItemShouldBe(product, 3);
        $("#cart-count").shouldBe(exactText("3"));
        $("#total-price").shouldBe(exactText("300"));

        $("#makeOrder").click();

        notificationShouldBe("Заказ принят в обработку!");
        $("#main-title").shouldBe(exactText("Заказ успешно оформлен!"));
        $("#cartModal").shouldBe(hidden);
        $("#cart-count").shouldBe(exactText("0"));
        $("#open-cart-btn").click();
        $("#empty-cart").shouldBe(visible, exactText("Пусто"));
        $("#total-price").shouldBe(exactText("0"));
    }

    @Test
    @DisplayName("3.2. Общая цена разных товаров учитывает цены и количество")
    void totalIsCalculatedForDifferentProducts() {
        var first = products.create("45.50");
        var second = products.create("70.25");
        var third = products.create("30");
        BigDecimal expected = first.price().multiply(BigDecimal.valueOf(2))
                .add(second.price()).add(third.price().multiply(BigDecimal.valueOf(3)));
        open("/");
        addToCart(first, 2);
        addToCart(second, 1);
        addToCart(third, 3);
        $("#open-cart-btn").click();

        cartItemShouldBe(first, 2);
        cartItemShouldBe(second, 1);
        cartItemShouldBe(third, 3);
        $("#cart-count").shouldBe(exactText("6"));
        $("#total-price").shouldBe(exactText(expected.stripTrailingZeros().toPlainString()));
    }

    @Test
    @DisplayName("3.3. После добавления товара в админке появляется уведомление")
    void creatingProductShowsNotification() {
        String name = products.newName();
        login(UiConfig.USER, UiConfig.PASSWORD);

        createProductInAdmin(name, "90");

        notificationShouldBe("Товар успешно добавлен!");
        $("#tbody input[value='" + name + "']").shouldBe(visible, exactValue(name));
        $("#n-name").shouldBe(exactValue(""));
        $("#n-price").shouldBe(exactValue(""));
    }

    @Test
    @DisplayName("3.4. Изменённые имя и цена сохраняются в списке товаров")
    void editedProductIsPersistedInList() {
        // 3.5: подготовка через POST /goods/add. 3.6: удаление ID в @AfterEach базового класса.
        var product = products.create("50");
        String updatedName = products.newName();
        login(UiConfig.USER, UiConfig.PASSWORD);
        $("#nm-" + product.id()).setValue(updatedName);
        $("#pr-" + product.id()).setValue("85.5");
        $("[data-action='update'][data-id='" + product.id() + "']").click();
        notificationShouldBe("Товар #" + product.id() + " обновлен");

        // Уходим и заново открываем список, чтобы проверить данные с сервера,
        // а не только введённые значения в ещё не перезагруженных полях.
        $("a[href='/']").click();
        $("a[href='/admin']").click();

        $("#nm-" + product.id()).shouldBe(visible, exactValue(updatedName));
        $("#pr-" + product.id()).shouldBe(exactValue("85.5"));
    }
}
