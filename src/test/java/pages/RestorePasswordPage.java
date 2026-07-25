package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
// UI-тесты //ui
public class RestorePasswordPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By emailField = By.xpath(".//input[@name='email']");
    private final By restoreButton = By.xpath(".//button[text()='Восстановить']");
    private final By loginLink = By.xpath(".//a[@href='/login']");

    public RestorePasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
    }

    public void clickRestoreButton() {
        wait.until(ExpectedConditions.elementToBeClickable(restoreButton)).click();
    }

    public LoginPage clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return new LoginPage(driver);
    }
}