package api;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;
import model.RegisterResponse; // импорт нового класса

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private static final String BASE_URL =
            "https://stellarburgers.education-services.ru";

    private String accessToken; // сохраняем токен для удаления
    @Step("Создание случайного пользователя через API")
    public User createRandomUser() {

        String email =
                "test" + UUID.randomUUID() + "@mail.com";

        User newUser = new User(
                email,
                "Test1234",
                "Test User"
        );

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .header("Content-Type", "application/json")
                        .body(newUser)
                        .when()
                        .post("/api/auth/register");

        if (response.statusCode() == 200) {

            // Десериализуем в RegisterResponse
            RegisterResponse registerResponse =
                    response.body().as(RegisterResponse.class);

            // Сохраняем токен для удаления
            this.accessToken = registerResponse.getAccessToken();

            // Берём пользователя из ответа
            User userFromResponse = registerResponse.getUser();

            // Возвращаем User, пароль берём из исходного запроса
            return new User(
                    userFromResponse.getEmail(),
                    newUser.getPassword(),
                    userFromResponse.getName()
            );

        } else {

            throw new RuntimeException(
                    "Не удалось создать пользователя. Код: "
                            + response.statusCode()
            );
        }
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