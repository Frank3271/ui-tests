package tests;
import api.UserApiClient;
import model.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.User;
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

import static org.junit.Assert.assertTrue;
// IU тесты
public class LoginTests {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private UserApiClient userApiClient;
    private User testUser;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        mainPage = new MainPage(driver);
        userApiClient = new UserApiClient();
        // Создаём пользователя через API для тестов входа
        testUser = userApiClient.createRandomUser();
        mainPage.open();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        userApiClient.deleteUser();
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Description("Проверяет, что пользователь может войти, нажав кнопку на главной странице")
    public void testLoginFromMainPage() {
        loginPage = mainPage.clickLoginButton();
        loginPage.enterEmail(testUser.getEmail());
        loginPage.enterPassword(testUser.getPassword());
        loginPage.clickLoginButton();


        assertTrue("Должна быть главная страница", driver.getCurrentUrl().contains("/"));
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Проверяет, что клик по 'Личный кабинет' открывает страницу входа")
    public void testLoginViaPersonalAccount() {
        mainPage.clickPersonalAccount();
        assertTrue("Должна быть страница входа", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @DisplayName("Вход через ссылку 'Войти' на странице регистрации")
    @Description("Проверяет, что со страницы регистрации можно перейти на страницу входа")
    public void testLoginViaRegisterPage() {
        loginPage = mainPage.clickLoginButton();
        registerPage = loginPage.clickRegisterLink();
        loginPage = registerPage.clickLoginLink();
        assertTrue("Должна быть страница входа", driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @DisplayName("Вход через ссылку 'Войти' на странице восстановления пароля")
    @Description("Проверяет, что со страницы восстановления пароля можно перейти на страницу входа")
    public void testLoginViaRestorePasswordPage() {
        loginPage = mainPage.clickLoginButton();
        loginPage.clickRestorePasswordLink();
        assertTrue("Должна быть страница восстановления", driver.getCurrentUrl().contains("/forgot-password"));
    }
}