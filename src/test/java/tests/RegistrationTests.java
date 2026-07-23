package tests;
// IU тесты
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
import pages.LoginPage;
import pages.RegisterPage;
import io.github.bonigarcia.wdm.WebDriverManager;
//ui
import static org.junit.Assert.assertTrue;

public class RegistrationTests {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    @Description("Проверяет, что после заполнения всех полей валидными данными пользователь регистрируется и переходит на страницу входа")
    public void testSuccessfulRegistration() {
        loginPage = mainPage.clickLoginButton();
        registerPage = loginPage.clickRegisterLink();


        String email = "ui_user_" + System.currentTimeMillis() + "@mail.com";
        String password = "password123";
        String name = "UITestUser";

        registerPage.enterName(name);
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.clickRegisterButton();

        assertTrue("Должна быть страница входа", loginPage.isLoginButtonDisplayed());
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Проверяет, что при вводе пароля меньше 6 символов появляется сообщение об ошибке")
    public void testRegistrationWithShortPassword() {
        loginPage = mainPage.clickLoginButton();
        registerPage = loginPage.clickRegisterLink();

        registerPage.enterName("NewUser");
        registerPage.enterEmail("test@mail.com");
        registerPage.enterPassword("12345"); // меньше 6 символов
        registerPage.clickRegisterButton();

        assertTrue("Должна появиться ошибка о коротком пароле",
                registerPage.isErrorMessageDisplayed());
    }
}