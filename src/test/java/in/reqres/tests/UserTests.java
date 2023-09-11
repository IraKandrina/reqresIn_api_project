package in.reqres.tests;

import in.reqres.models.*;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static in.reqres.specs.Specs.*;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("IraKandrina")
@Epic("API Reqres")
@Feature("Actions with users")
public class UserTests {
    UserModel user = new UserModel();

    @Severity(CRITICAL)
    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        user.setName("morpheus");
        user.setJob("leader");

        UserResponseModel userResponse = step("Make request", () ->
                given(request)
                        .body(user)
                        .when()
                        .post("/users")
                        .then()
                        .spec(response201)
                        .extract().as(UserResponseModel.class));

        step("Check response", () -> {
            assertThat(userResponse.getName()).isEqualTo("morpheus");
            assertThat(userResponse.getJob()).isEqualTo("leader");
        });
    }

    @Severity(NORMAL)
    @Test
    @DisplayName("Обновление данных пользователя")
    void updateUserTest() {
        user.setName("morpheus");
        user.setJob("zion resident");

        UserResponseModel userResponse = step("Make request", () ->
                given(request)
                        .body(user)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(response200)
                        .extract().as(UserResponseModel.class));

        step("Check response", () -> {
            assertThat(userResponse.getName()).isEqualTo("morpheus");
            assertThat(userResponse.getJob()).isEqualTo("zion resident");
        });
    }

    @Severity(NORMAL)
    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {
        step("Delete user", () ->
                given(request)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(response204));
    }

}
