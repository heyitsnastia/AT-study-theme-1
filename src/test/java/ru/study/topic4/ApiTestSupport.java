package ru.study.topic4;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;

@Tag("api")
abstract class ApiTestSupport {
    static final String BASE_URL = System.getProperty("api.baseUrl", "http://localhost:8080");
    private static final String USER = System.getProperty("api.user", "admin");
    private static final String PASSWORD = System.getProperty("api.password", "secret123");
    private final List<Long> createdIds = new ArrayList<>();

    RequestSpecification request() {
        return RestAssured.given().baseUri(BASE_URL);
    }

    RequestSpecification authorizedRequest() {
        return request().auth().preemptive().basic(USER, PASSWORD);
    }

    String uniqueName() {
        return "api-test-" + UUID.randomUUID();
    }

    Long createProduct(String name, double price) {
        Long id = authorizedRequest()
                .contentType("application/json")
                .body(Map.of("name", name, "price", price))
                .when().post("/goods/add")
                .then().statusCode(200)
                .body("data.id", notNullValue())
                .extract().jsonPath().getLong("data.id");
        createdIds.add(id);
        return id;
    }

    void markDeleted(Long id) {
        createdIds.remove(id);
    }

    @AfterEach
    void deleteCreatedProducts() {
        for (Long id : createdIds) {
            authorizedRequest()
                    .when().delete("/goods/{id}", id)
                    .then().statusCode(200);
        }
    }
}
