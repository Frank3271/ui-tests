package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.Assert.assertEquals;
// IU тесты
public class ConstructorTests {

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {

        String browser = System.getProperty("browser", "chrome");
        if (browser.equals("yandex")) {

            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Users\\Lecoo\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
            driver = new ChromeDriver(options);
        } else {

            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);
        }
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
    @DisplayName("Переход к разделу 'Булки'")
    @Description("Проверяет, что при клике на раздел 'Булки' вкладка становится активной")
    public void testSwitchToBunSection() {
        mainPage.clickBunSection();
        assertEquals("Должен быть выбран раздел 'Булки'", "Булки", mainPage.getActiveSectionTabText());
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверяет, что при клике на раздел 'Соусы' вкладка становится активной")
    public void testSwitchToSauceSection() {
        mainPage.clickSauceSection();
        assertEquals("Должен быть выбран раздел 'Соусы'", "Соусы", mainPage.getActiveSectionTabText());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверяет, что при клике на раздел 'Начинки' вкладка становится активной")
    public void testSwitchToFillingSection() {
        mainPage.clickFillingSection();
        assertEquals("Должен быть выбран раздел 'Начинки'", "Начинки", mainPage.getActiveSectionTabText());
    }
}