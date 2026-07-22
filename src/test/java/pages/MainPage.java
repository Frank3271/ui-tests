package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//a[@href='/account']");
    private final By constructorButton = By.xpath(".//a[@href='/']");

    // Разделы конструктора — используем классы, а не текст
    private final By bunSection = By.xpath(".//span[text()='Булки']/parent::div");
    private final By sauceSection = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingSection = By.xpath(".//span[text()='Начинки']/parent::div");

    // Активная вкладка — ищем по классу
    private final By activeSectionTab = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://stellarburgers.education-services.ru/");
        // Дополнительное ожидание загрузки страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }

    public LoginPage clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new LoginPage(driver);
    }

    public void clickPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton)).click();
    }

    public void clickConstructor() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorButton)).click();
    }

    public void clickBunSection() {
        wait.until(ExpectedConditions.elementToBeClickable(bunSection)).click();
    }

    public void clickSauceSection() {
        wait.until(ExpectedConditions.elementToBeClickable(sauceSection)).click();
    }

    public void clickFillingSection() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingSection)).click();
    }

    public String getActiveSectionTabText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeSectionTab)).getText();
    }
}