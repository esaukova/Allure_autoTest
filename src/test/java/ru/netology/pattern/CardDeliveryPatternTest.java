package ru.netology.pattern;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import ru.netology.data.DataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class CardDeliveryPatternTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach  //перед выполнением тестов
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage"); //overcome limited resource problems
        options.addArguments("--no-sandbox");  //Bypass OS security model
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {  //после выполнения тестов
        driver.quit();
        driver = null;
    }

    // DateService service = new DateService();

    @Test

    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        driver.findElement(By.cssSelector("[data-test-id='city'] input")).sendKeys(validUser.getCity());
        driver.findElement(By.cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        driver.findElement(By.cssSelector("[data-test-id=date] input")).sendKeys(firstMeetingDate);
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(validUser.getName());
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys(validUser.getPhone());
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[class='button__content']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".notification__content"))));
        String text = driver.findElement(By.cssSelector("[data-test-id='success-notification'] .notification__content")).getText();
        assertEquals("Встреча успешно запланирована на " + firstMeetingDate, text.trim());
        driver.findElement(By.cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        driver.findElement(By.cssSelector("[data-test-id=date] input")).sendKeys(secondMeetingDate);
        driver.findElement(By.cssSelector("[class='button__content']")).click();
        String textConfirmation = driver.findElement(By.cssSelector("[data-test-id='replan-notification'] .notification__title")).getText();
        assertEquals("Необходимо подтверждение", textConfirmation.trim());
        driver.findElement(By.cssSelector("[data-test-id='replan-notification'] span.button__text")).click();
        String textReplan = driver.findElement(By.cssSelector("[data-test-id=success-notification] .notification__content")).getText();
        assertEquals("Встреча успешно запланирована на " + secondMeetingDate, textReplan.trim());

    }

//    @Test
//
//    void shouldTestPositiveScenarioForDeliveryForm() {
//
//        driver.findElement(By.cssSelector("[data-test-id='city'] input")).sendKeys("Санкт-Петербург");
//        String meetingDate = service.date(3, "dd.MM.yyyy");
//        driver.findElement(By.cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
//        driver.findElement(By.cssSelector("[data-test-id=date] input")).sendKeys(meetingDate);
//        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Иванов-Петров");
//        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79270000000");
//        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
//        driver.findElement(By.cssSelector("[class='button__content']")).click();
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".notification__content"))));
//        String text = driver.findElement(By.cssSelector(".notification__content")).getText();
//        assertEquals("Встреча успешно забронирована на " + meetingDate, text.trim());
//
//    }


//    @Test
//    void shouldTestAdvancePositiveScenarioForDeliveryForm() {
//
//        $("[data-test-id='city'] input").setValue("Са");
//        $$("[class='menu-item__control']").findBy(text("Санкт-Петербург")).click();
//        LocalDate meetingDate = LocalDate.now().plusDays(14);
//        $("[class='input__icon']").click();
//        if (LocalDate.now().getMonthValue() != meetingDate.getMonthValue()) {
//            $("[data-step='1'].calendar__arrow_direction_right").click();
//        }
//        $$("[data-day]").findBy(Condition.text(String.valueOf(meetingDate.getDayOfMonth()))).click();
//        $("[data-test-id='name'] input").setValue("Иван Иванов-Петров");
//        $("[data-test-id='phone'] input").setValue("+79270000000");
//        $("[data-test-id='agreement']").click();
//        $("[class='button__content']").click();
//        $(".notification__content")
//                .shouldHave(exactText("Встреча успешно забронирована на " + meetingDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))), Duration.ofSeconds(15));
//
//    }


}
