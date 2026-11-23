package ru.study.topic6.support;

import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;

/** API используется только для подготовки и удаления принадлежащих тесту данных. */
public final class ProductFixtures {
    private final Set<String> names = new LinkedHashSet<>();
    private final Set<Long> ids = new LinkedHashSet<>();

    public record Product(long id, String name, BigDecimal price) {
    }

    public String newName() {
        String name = "ui-test-" + UUID.randomUUID();
        // Регистрируем имя до создания: cleanup найдёт товар и при падении UI-проверки.
        names.add(name);
        return name;
    }

    public Product create(String price) {
        String name = newName();
        BigDecimal amount = new BigDecimal(price);
        Response response = request().contentType("application/json")
                .body(Map.of("name", name, "price", amount)).post("/goods/add");
        requireStatus(response, 200, "Создание тестового товара");
        Long id = response.jsonPath().getLong("data.id");
        if (id == null) {
            throw new IllegalStateException("API не вернул data.id созданного товара");
        }
        ids.add(id);
        return new Product(id, name, amount);
    }

    public void deleteCreatedProducts() {
        if (names.isEmpty() && ids.isEmpty()) {
            return;
        }
        List<RuntimeException> failures = new ArrayList<>();
        try {
            // Сначала собираем ID со всех страниц, затем удаляем: пагинация не сдвигается.
            for (int page = 0; ; page++) {
                Response response = request().queryParam("page", page).queryParam("size", 100)
                        .get("/goods/list");
                requireStatus(response, 200, "Поиск тестовых товаров для удаления");
                List<Map<String, Object>> goods = response.jsonPath().getList("goods");
                for (Map<String, Object> good : goods) {
                    if (names.contains(good.get("name"))) {
                        ids.add(((Number) good.get("id")).longValue());
                    }
                }
                if (goods.size() < 100) {
                    break;
                }
            }
        } catch (RuntimeException failure) {
            failures.add(failure);
        }
        for (long id : ids) {
            try {
                Response response = request().delete("/goods/{id}", id);
                if (response.statusCode() != 404) {
                    requireStatus(response, 200, "Удаление тестового товара " + id);
                }
            } catch (RuntimeException failure) {
                failures.add(failure);
            }
        }
        if (!failures.isEmpty()) {
            IllegalStateException failure = new IllegalStateException("Не удалось полностью очистить тестовые данные");
            failures.forEach(failure::addSuppressed);
            throw failure;
        }
        ids.clear();
        names.clear();
    }

    private RequestSpecification request() {
        return given().baseUri(UiConfig.BASE_URL)
                .auth().preemptive().basic(UiConfig.USER, UiConfig.PASSWORD)
                .config(RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10_000)
                        .setParam("http.socket.timeout", 10_000)));
    }

    private void requireStatus(Response response, int expected, String operation) {
        if (response.statusCode() != expected) {
            throw new IllegalStateException(operation + ": ожидался HTTP " + expected
                    + ", получен " + response.statusCode() + ": " + response.asString());
        }
    }
}
