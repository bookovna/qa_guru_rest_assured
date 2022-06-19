import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RestAssuredTest {

    @BeforeAll
    static void settings() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void successfulLogin() {
        String authorizeData = "{\"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\"}";

        given()
                .body(authorizeData)
                .contentType(JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }

    @Test
    void missingPasswordLogin() {
        String authorizedData = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .body(authorizedData)
                .contentType(JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void resourceNotFound404() {

        given()
                .when()
                .get("/unknown/23")
                .then()
                .statusCode(404);
    }

    @Test
    void getSingleUser() {
        given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data", is(notNullValue()));
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void singleUserNotFound() {
        given()
                .when()
                .get("/users/23")
                .then()
                .statusCode(404);
    }
}
