package ru.study.topic4;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;

class GoodsListLectureTest extends ApiTestSupport {
    @Test
    void emptyListWithGivenWhenThen() {
        // A page beyond the created products is empty even if the API already has data.
        request()
                .given().queryParam("page", 1000)
                .when().get("/goods/list")
                .then().statusCode(200)
                .body("goods", empty());
    }

    @Test
    void emptyListWithRequestSpecification() {
        RequestSpecification specification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addQueryParam("page", 1000)
                .build();

        given().spec(specification)
                .when().get("/goods/list")
                .then().statusCode(200)
                .body("goods", empty());
    }

    @Test
    void addedProductAppearsInListWithRestAssuredAssertions() {
        Long id = createProduct(uniqueName(), 12.5);

        request()
                .queryParam("size", 20)
                .when().get("/goods/list")
                .then().statusCode(200)
                .body("goods.id", hasItem(id.intValue()));
    }

    @Test
    void addedProductAppearsInListWithAssertJ() {
        String name = uniqueName();
        Long id = createProduct(name, 15.5);

        List<Map<String, Object>> goods = request()
                .queryParam("size", 20)
                .when().get("/goods/list")
                .then().statusCode(200)
                .extract().jsonPath().getList("goods");

        assertThat(goods).anySatisfy(good -> {
            assertThat(((Number) good.get("id")).longValue()).isEqualTo(id);
            assertThat(good.get("name")).isEqualTo(name);
            assertThat(((Number) good.get("price")).doubleValue()).isEqualTo(15.5);
        });
    }
}
