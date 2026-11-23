package ru.study.topic6.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.study.topic6.support.UiConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тема 6. Задача 1 — Selenium")
class SeleniumLectureTest extends SeleniumTestSupport {
    @Test
    @DisplayName("1.1. Добавленный через админку товар отображается на витрине")
    void createdProductAppearsOnStorefront() {
        String name = products.newName();
        login(UiConfig.USER, UiConfig.PASSWORD);
        visible("#n-name").sendKeys(name);
        visible("#n-price").sendKeys("75");
        click("#add-btn");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("#toast-container"), "Товар успешно добавлен!"));
        click("a[href='/']");

        String selector = ".product-card[data-name='" + name + "']";
        assertTrue(visible(selector).isDisplayed());
        assertEquals(name, visible(selector + " h4").getText());
        assertEquals("75", visible(selector).getDomAttribute("data-price"));
    }

    @Test
    @DisplayName("1.2. Добавленный товар отображается в корзине")
    void addedProductAppearsInCart() {
        var product = products.create("80");
        addToCart(product, 1);

        assertEquals(product.name(), visible("#cart-item-" + product.id() + " b").getText());
        assertEquals("1", visible("#cart-item-" + product.id() + " .qty-controls span").getText());
        assertEquals("80", visible("#total-price").getText());
    }

    @Test
    @DisplayName("1.3. Неверные логин и пароль не дают войти в админку")
    void invalidCredentialsAreRejected() {
        login("invalid-" + products.newName(), "incorrect-password");

        // Spring Security локализует текст ошибки по языку браузера.
        assertFalse(visible(".alert-danger").getText().isBlank());
        assertTrue(driver.getCurrentUrl().contains("/login?error"));
        driver.get(UiConfig.BASE_URL + "/admin");
        assertTrue(visible("#username").isDisplayed());
        assertTrue(driver.findElements(By.id("n-name")).isEmpty());
    }

    @Test
    @DisplayName("1.4. Товары и их количество сохраняются после обновления страницы")
    void cartSurvivesPageRefresh() {
        var product = products.create("60");
        addToCart(product, 2);
        String item = "#cart-item-" + product.id();
        assertEquals(product.name(), visible(item + " b").getText());
        assertEquals("2", visible(item + " .qty-controls span").getText());
        assertEquals("120", visible("#total-price").getText());

        driver.navigate().refresh();
        click("#open-cart-btn");

        assertEquals(product.name(), visible(item + " b").getText());
        assertEquals("2", visible(item + " .qty-controls span").getText());
        assertEquals("2", visible("#cart-count").getText());
        assertEquals("120", visible("#total-price").getText());
    }
}
