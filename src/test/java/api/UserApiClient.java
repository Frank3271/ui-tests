package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;
// IU тесты
import static io.restassured.RestAssured.given;

public class UserApiClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private String accessToken;

    @Step("Создание случайного пользователя через API")
    public User createRandomUser() {
        String email = "user_" + System.currentTimeMillis() + "@mail.com";
        String password = "password123";
        String name = "TestUser";
        User user = new User(email, password, name);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post(BASE_URL + "/api/auth/register");

        accessToken = response.jsonPath().getString("accessToken");
        return user;
    }

    @Step("Удаление пользователя через API")
    public void deleteUser() {
        if (accessToken != null) {
            given()
                    .header("Authorization", accessToken)
                    .when()
                    .delete(BASE_URL + "/api/auth/user");
        }
    }
}