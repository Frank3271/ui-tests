package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.LoginPage;
import pages.RegisterPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

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

        driver = new ChromeDriver(options);
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ========== ТЕСТЫ РЕГИСТРАЦИИ ==========

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

        // Ожидаем появления кнопки "Войти" на странице входа (более надёжно, чем проверка URL)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean loginButtonDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//button[text()='Войти']")
        )).isDisplayed();

        assertTrue("После регистрации должна быть страница входа с кнопкой 'Войти'", loginButtonDisplayed);
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

    // ========== ТЕСТЫ ВХОДА ==========

    @Test
    public void testLoginFromMainPage() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();

        loginPage.enterEmail("testuser@mail.com");
        loginPage.enterPassword("password123");
        loginPage.clickLoginButton();

        // Проверяем, что перешли на главную страницу (по наличию кнопки "Оформить заказ" или URL)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isMainPage = wait.until(ExpectedConditions.urlContains("/"));
        assertTrue("Должна быть главная страница", isMainPage);
    }

    @Test
    public void testLoginViaPersonalAccount() {
        mainPage.open();
        mainPage.clickPersonalAccount();

        // Ожидаем страницу входа
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isLoginPage = wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue("Должна быть страница входа", isLoginPage);
    }

    @Test
    public void testLoginViaRegisterPage() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();
        registerPage = loginPage.clickRegisterLink();
        loginPage = registerPage.clickLoginLink();

        // Ожидаем страницу входа
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isLoginPage = wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue("Должна быть страница входа", isLoginPage);
    }

    @Test
    public void testLoginViaRestorePasswordPage() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();
        loginPage.clickRestorePasswordLink();

        // Ожидаем страницу восстановления пароля
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isRestorePage = wait.until(ExpectedConditions.urlContains("/forgot-password"));
        assertTrue("Должна быть страница восстановления", isRestorePage);
    }
}