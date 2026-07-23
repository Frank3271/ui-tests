package api;

import io.restassured.response.Response;
import model.User;
// ui
import static io.restassured.RestAssured.given;
//ui
public class UserApiClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private String accessToken;

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


        if (response.statusCode() == 200 || response.statusCode() == 201) {
            accessToken = response.jsonPath().getString("accessToken");
        } else {
            System.err.println("Ошибка создания пользователя: " + response.statusCode());
        }

        return user;
    }

    public void deleteUser() {
        if (accessToken != null) {
            Response response = given()
                    .header("Authorization", accessToken)
                    .when()
                    .delete(BASE_URL + "/api/auth/user");
            if (response.statusCode() != 202 && response.statusCode() != 200) {
                System.err.println("Ошибка удаления пользователя: " + response.statusCode());
            }
        }
    }
}