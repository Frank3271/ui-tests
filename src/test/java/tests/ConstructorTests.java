package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.Assert.assertEquals;
// UI-тесты
public class ConstructorTests {

    private WebDriver driver;
    private MainPage mainPage;

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
    public void testSwitchToBunSection() {
        mainPage.clickBunSection();
        assertEquals("Должен быть выбран раздел 'Булки'", "Булки", mainPage.getActiveSectionTabText());
    }

    @Test
    public void testSwitchToSauceSection() {
        mainPage.clickSauceSection();
        assertEquals("Должен быть выбран раздел 'Соусы'", "Соусы", mainPage.getActiveSectionTabText());
    }

    @Test
    public void testSwitchToFillingSection() {
        mainPage.clickFillingSection();
        assertEquals("Должен быть выбран раздел 'Начинки'", "Начинки", mainPage.getActiveSectionTabText());
    }
}