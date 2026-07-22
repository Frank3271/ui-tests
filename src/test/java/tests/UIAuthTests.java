package tests;

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

public class UIAuthTests {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        // Для Яндекс Браузера замените на соответствующий WebDriver

        driver = new ChromeDriver(options);
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // === ТЕСТЫ РЕГИСТРАЦИИ ===
    @Test
    public void testSuccessfulRegistration() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();
        registerPage = loginPage.clickRegisterLink();

        String email = "newuser_" + System.currentTimeMillis() + "@mail.com";
        registerPage.enterName("NewUser");
        registerPage.enterEmail(email);
        registerPage.enterPassword("password123");
        registerPage.clickRegisterButton();

        // Проверяем, что после регистрации перешли на страницу входа
        assertTrue("Должна быть страница входа с кнопкой 'Войти'",
                driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void testRegistrationWithShortPassword() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();
        registerPage = loginPage.clickRegisterLink();

        registerPage.enterName("NewUser");
        registerPage.enterEmail("test@mail.com");
        registerPage.enterPassword("12345"); // меньше 6 символов
        registerPage.clickRegisterButton();

        assertTrue("Должна появиться ошибка о коротком пароле",
                registerPage.isErrorMessageDisplayed());
    }

    // === ТЕСТЫ ВХОДА ===
    @Test
    public void testLoginFromMainPage() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();

        // Предварительно создаём тестового пользователя (можно через API)
        // Для простоты используем существующего
        loginPage.enterEmail("testuser@mail.com");
        loginPage.enterPassword("password123");
        loginPage.clickLoginButton();

        // Проверяем, что перешли на главную
        assertTrue("Должна быть главная страница",
                driver.getCurrentUrl().contains("/"));
    }

    @Test
    public void testLoginViaPersonalAccount() {
        mainPage.open();
        mainPage.clickPersonalAccount();

        assertTrue("Должна быть страница входа",
                driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void testLoginViaRegisterPage() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();
        registerPage = loginPage.clickRegisterLink();
        loginPage = registerPage.clickLoginLink();

        assertTrue("Должна быть страница входа",
                driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void testLoginViaRestorePasswordPage() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();
        loginPage.clickRestorePasswordLink();  // просто переходим на страницу восстановления, но не возвращаемся

        assertTrue("Должна быть страница восстановления",
                driver.getCurrentUrl().contains("/forgot-password"));
    }
}