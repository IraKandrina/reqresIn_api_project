package in.reqres.tests;

import in.reqres.models.*;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static in.reqres.specs.Specs.*;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@Owner("IraKandrina")
@Epic("API Reqres")
@Feature("User authorization")
public class LoginTests {
    UserModel user = new UserModel();
    @Severity(CRITICAL)
    @Test
    @DisplayName("Успешный логин пользователя")
    void successfulLoginTest() {
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("cityslicka");

        ResponseModel response = step("Make request", () ->
                given(request)
                        .body(user)
                        .when()
                        .post("/login")
                        .then()
                        .spec(response200)
                        .extract().as(ResponseModel.class));
        step("Verify response", () ->
                assertThat(response.getToken()).isNotNull());
    }

    @Severity(NORMAL)
    @Test
    @DisplayName("Неуспешный логин пользователя")
    void unsuccessfulLogin() {
        user.setEmail("test.holt@reqres.in");

        step("Make request", () ->
                given(request)
                        .body(user)
                        .when()
                        .post("/login")
                        .then()
                        .spec(response400))
                .body("error", is("user not found"));
    }
}
