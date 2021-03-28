package ru.netology;

import org.junit.jupiter.api.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class AppOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("--headless");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldTest() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иванов-Петров Иван");
        textFields.get(1).sendKeys("+77777777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldFailNameTest() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Ivanov Ivan");
        textFields.get(1).sendKeys("+77777777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(By.className("input__sub")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldFailPhoneTest() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иванов-Петров Иван");
        textFields.get(1).sendKeys("87777777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        List<WebElement> numberFields = driver.findElements(By.className("input__sub"));
        String actualMessage = numberFields.get(1).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldFailNameAndPhoneTest() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Ivanov Ivan");
        textFields.get(1).sendKeys("87777777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        List<WebElement> numberFields = driver.findElements(By.className("input__sub"));
        String actualMessage = numberFields.get(0).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }

    @Test
    void shouldFailWithNoCheckboxTest() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иванов-Петров Иван");
        textFields.get(1).sendKeys("+77777777777");
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(By.className("checkbox__text")).getText();
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedMessage, actualMessage.strip());
    }
}
