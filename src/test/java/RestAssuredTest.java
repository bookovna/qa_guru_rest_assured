import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RestAssuredTest {

    @Test
    void successfulLogin() {
        String authorizeData = "{\"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\"}";

        given()
                .body(authorizeData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
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
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void resourceNotFound404() {

        given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404);
    }

    @Test
    void getSingleUser() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data", is(notNullValue()));
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void singleUserNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }
}
