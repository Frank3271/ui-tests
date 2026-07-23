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
// UI-тесты
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



    @Test
    public void testLoginFromMainPage() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();

        loginPage.enterEmail("testuser@mail.com");
        loginPage.enterPassword("password123");
        loginPage.clickLoginButton();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isMainPage = wait.until(ExpectedConditions.urlContains("/"));
        assertTrue("Должна быть главная страница", isMainPage);
    }

    @Test
    public void testLoginViaPersonalAccount() {
        mainPage.open();
        mainPage.clickPersonalAccount();

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


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isLoginPage = wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue("Должна быть страница входа", isLoginPage);
    }

    @Test
    public void testLoginViaRestorePasswordPage() {
        mainPage.open();
        loginPage = mainPage.clickLoginButton();
        loginPage.clickRestorePasswordLink();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isRestorePage = wait.until(ExpectedConditions.urlContains("/forgot-password"));
        assertTrue("Должна быть страница восстановления", isRestorePage);
    }
}