package tests;
// IU
import api.UserApiClient;
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
//ui
import static org.junit.Assert.assertTrue;

public class RegistrationTests {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private UserApiClient userApiClient;
    @Before
    public void setUp() {
        // Выбор браузера через системную переменную (по умолчанию chrome)
        String browser = System.getProperty("browser", "chrome");

        if (browser.equals("yandex")) {
            // Для Яндекс Браузера используем ChromeDriver с указанием пути к исполняемому файлу
            System.setProperty("webdriver.chrome.driver", "путь_к_драйверу"); // или используйте WebDriverManager
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Users\\Lecoo\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
            driver = new ChromeDriver(options);
        } else {
            // По умолчанию Google Chrome
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);
        }

        mainPage = new MainPage(driver);
        userApiClient = new UserApiClient();
        mainPage.open();
    }

    @After
    public void tearDown() {
        if (userApiClient != null) {
            try {
                userApiClient.deleteUser();
            } catch (Exception e) {
                System.out.println("Не удалось удалить пользователя: " + e.getMessage());
            }
        }
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