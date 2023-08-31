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
@Feature("User registration")
public class RegisterTests {
    UserModel user = new UserModel();
    @Severity(CRITICAL)
    @Test
    @DisplayName("Успешная регистрация пользователя")
    void successfulRegistrationTest() {
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");

        ResponseModel response = step("Make request", () ->
                given(request)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(response200)
                        .extract().as(ResponseModel.class));
        step("Verify response", () ->
                assertThat(response.getToken()).isNotNull());
    }

    @Severity(NORMAL)
    @Test
    @DisplayName("Ошибка регистрации пользователя")
    void unsuccessfulRegistrationTest() {
        user.setEmail("sydney@fife");

        step("Make request", () ->
                given(request)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(response400))
                .body("error", is("Missing password"));
    }
}
