package ru.study.topic7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.study.topic6.selenide.SelenideTestSupport;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.DragAndDropOptions.to;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Tag("topic7-lecture")
@DisplayName("Тема 7. Лекция — BP и типичные проблемы")
class CartLectureTest extends SelenideTestSupport {
    @Test
    @DisplayName("1.1. Перетаскивание карточки товара в корзину")
    void draggedProductAppearsInCart() {
        var product = products.create("80");
        open("/");
        $("#cart-count").shouldBe(exactText("0"));

        // HTML5 Drag-and-Drop передаёт данные через DataTransfer.
        $("#card-" + product.id()).shouldBe(visible)
                .dragAndDrop(to("#open-cart-btn").usingJS());
        $("#open-cart-btn").click();

        cartItemShouldBe(product, 1);
        $("#cart-count").shouldBe(exactText("1"));
        $("#total-price").shouldBe(exactText("80"));
    }

    @Test
    @DisplayName("1.2. Удалённый товар больше не отображается в корзине")
    void removedProductDisappearsFromCart() {
        var product = products.create("60");
        open("/");
        addToCart(product, 1);
        $("#open-cart-btn").click();
        cartItemShouldBe(product, 1);

        $("#cart-item-" + product.id() + " [data-action='remove']").click();

        $("#cartModal").shouldBe(visible);
        $("#cart-item-" + product.id()).shouldNot(exist);
        $("#empty-cart").shouldBe(visible, exactText("Пусто"));
        $("#cart-count").shouldBe(exactText("0"));
        $("#total-price").shouldBe(exactText("0"));
    }
}
