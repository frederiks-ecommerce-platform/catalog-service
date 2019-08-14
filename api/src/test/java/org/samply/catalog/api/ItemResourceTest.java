package org.samply.catalog.api;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.samply.catalog.api.model.Category;
import org.samply.catalog.api.model.ItemCreationDTO;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ItemResourceTest {

    @Test
    public void testHelloEndpoint() {
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

}
