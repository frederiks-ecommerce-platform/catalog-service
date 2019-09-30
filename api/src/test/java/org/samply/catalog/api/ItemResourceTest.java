package org.samply.catalog.api;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.samply.catalog.api.domain.model.Category;
import org.samply.catalog.api.domain.model.ItemCreationDTO;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ItemResourceTest {

    @Test
    public void postItemSuccessfully() {
        ItemCreationDTO inputItem = new ItemCreationDTO(
                "title",
                "description",
                BigDecimal.valueOf(9.99),
                Category.A
        );

        given().contentType(JSON)
               .body(inputItem)
               .header("X-User-Id", "123456")
               .when()
               .post("/items")
               .then()
               .assertThat()
               .statusCode(201)
               .contentType(JSON)
               .body("id", notNullValue())
               .body("title", equalTo(inputItem.getTitle()))
               .body("description", equalTo(inputItem.getDescription()))
               .body("price", equalTo(inputItem.getPrice().floatValue()))
               .body("category", equalTo(inputItem.getCategory().name()));
    }

    @Test
    public void postItemWithNullUserIdIsInvalid() {
        ItemCreationDTO inputItem = new ItemCreationDTO(
                "title",
                "description",
                BigDecimal.valueOf(9.99),
                Category.A
        );

        given().contentType(JSON)
               .body(inputItem)
               .when()
               .post("/items")
               .then()
               .assertThat()
               .statusCode(400)
               .contentType(JSON);
    }

    @Test
    public void postItemWithEmptyUserIdIsInvalid() {
        ItemCreationDTO inputItem = new ItemCreationDTO(
                "title",
                "description",
                BigDecimal.valueOf(9.99),
                Category.A
        );

        given().contentType(JSON)
               .header("X-User-Id", "")
               .body(inputItem)
               .when()
               .post("/items")
               .then()
               .assertThat()
               .statusCode(400)
               .contentType(JSON);
    }

    @TestFactory
    Stream<DynamicTest> ensureValidationForBadData() {
        return Stream.of(
                new ItemCreationDTO(
                        null,
                        "description",
                        BigDecimal.valueOf(9.99),
                        Category.A
                ),
                new ItemCreationDTO(
                        "",
                        "description",
                        BigDecimal.valueOf(9.99),
                        Category.A
                ),
                new ItemCreationDTO(
                        "title",
                        null,
                        BigDecimal.valueOf(9.99),
                        Category.A
                ),
                new ItemCreationDTO(
                        "title",
                        "",
                        BigDecimal.valueOf(9.99),
                        Category.A
                ),
                new ItemCreationDTO(
                        "title",
                        "description",
                        null,
                        Category.A
                ),
                new ItemCreationDTO(
                        "title",
                        "description",
                        BigDecimal.valueOf(-1.99),
                        Category.A
                ),
                new ItemCreationDTO(
                        "title",
                        "description",
                        BigDecimal.valueOf(9.99),
                        null
                )
        ).map(input -> dynamicTest(input.toString(), () -> postWithUserIdShouldBeInvalid(input)));
    }

    private void postWithUserIdShouldBeInvalid(ItemCreationDTO inputItem) {
        given().contentType(JSON)
               .body(inputItem)
               .header("X-User-Id", "123456")
               .when()
               .post("/items")
               .then()
               .assertThat()
               .statusCode(400)
               .contentType(JSON);
    }

}
