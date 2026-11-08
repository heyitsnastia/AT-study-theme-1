package ru.study.topic4;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

class GoodsSwaggerTest extends ApiTestSupport {
    @Test
    void addReturns200AndCreatedId() {
        Long id = createProduct(uniqueName(), 20.0);

        authorizedRequest()
                .when().get("/goods/{id}", id)
                .then().statusCode(200)
                .body("id", equalTo(id.intValue()));
    }

    @Test
    void addReturns400ForEmptyName() {
        authorizedRequest()
                .contentType("application/json")
                .body(Map.of("name", "", "price", 20.0))
                .when().post("/goods/add")
                .then().statusCode(400)
                .body("name", equalTo("Name cannot be empty"));
    }

    @Test
    void addReturns400ForDuplicateName() {
        String name = uniqueName();
        createProduct(name, 20.0);

        authorizedRequest()
                .contentType("application/json")
                .body(Map.of("name", name, "price", 25.0))
                .when().post("/goods/add")
                .then().statusCode(400)
                .body("message", containsString("already exists"));
    }

    @Test
    void listReturns200AndContainsCreatedProduct() {
        Long id = createProduct(uniqueName(), 20.0);

        request()
                .queryParam("size", 20)
                .when().get("/goods/list")
                .then().statusCode(200)
                .body("goods.id", hasItem(id.intValue()));
    }

    @Test
    void getReturns200ForExistingProduct() {
        String name = uniqueName();
        Long id = createProduct(name, 20.0);

        authorizedRequest()
                .when().get("/goods/{id}", id)
                .then().statusCode(200)
                .body("id", equalTo(id.intValue()))
                .body("name", equalTo(name))
                .body("price", equalTo(20.0f));
    }

    @Test
    void getReturns404ForMissingProduct() {
        authorizedRequest()
                .when().get("/goods/{id}", 0)
                .then().statusCode(404)
                .body("message", containsString("not found"));
    }

    @Test
    void patchReturns200AndUpdatesProduct() {
        Long id = createProduct(uniqueName(), 20.0);
        String newName = uniqueName();

        authorizedRequest()
                .contentType("application/json")
                .body(Map.of("name", newName, "price", 30.0))
                .when().patch("/goods/{id}", id)
                .then().statusCode(200)
                .body("id", equalTo(id.intValue()))
                .body("name", equalTo(newName))
                .body("price", equalTo(30.0f));
    }

    @Test
    void patchReturns400ForDuplicateName() {
        Long id = createProduct(uniqueName(), 20.0);
        String usedName = uniqueName();
        createProduct(usedName, 25.0);

        authorizedRequest()
                .contentType("application/json")
                .body(Map.of("name", usedName, "price", 30.0))
                .when().patch("/goods/{id}", id)
                .then().statusCode(400)
                .body(containsString("already exists"));
    }

    @Test
    void patchReturns404ForMissingProduct() {
        authorizedRequest()
                .contentType("application/json")
                .body(Map.of("name", uniqueName(), "price", 30.0))
                .when().patch("/goods/{id}", 0)
                .then().statusCode(404)
                .body(containsString("not found"));
    }

    @Test
    void deleteReturns200AndRemovesProduct() {
        Long id = createProduct(uniqueName(), 20.0);

        authorizedRequest()
                .when().delete("/goods/{id}", id)
                .then().statusCode(200)
                .body(containsString("deleted successfully"));
        markDeleted(id);

        authorizedRequest()
                .when().get("/goods/{id}", id)
                .then().statusCode(404)
                .body("message", notNullValue());
    }

    @Test
    void deleteReturns404ForMissingProduct() {
        authorizedRequest()
                .when().delete("/goods/{id}", 0)
                .then().statusCode(404)
                .body(containsString("not found"));
    }
}
