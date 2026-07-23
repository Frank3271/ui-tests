package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
// UI-тесты
public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//a[@href='/account']");
    private final By constructorButton = By.xpath(".//a[@href='/']");

    // Локаторы для вкладок конструктора — кликаем по самому span с текстом
    private final By bunSection = By.xpath(".//span[text()='Булки']");
    private final By sauceSection = By.xpath(".//span[text()='Соусы']");
    private final By fillingSection = By.xpath(".//span[text()='Начинки']");

    // Активная вкладка
    private final By activeSectionTab = By.xpath(".//div[contains(@class, 'tab_tab_type_current')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://stellarburgers.education-services.ru/");
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
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(bunSection));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickSauceSection() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(sauceSection));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickFillingSection() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(fillingSection));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public String getActiveSectionTabText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(activeSectionTab)).getText();
    }
}