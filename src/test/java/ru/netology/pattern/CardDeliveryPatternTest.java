package ru.netology.pattern;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

public class CardDeliveryPatternTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test

    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $("[class='button__content']").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $("[class='button__content']").click();
        $("[data-test-id='replan-notification'] .notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] span.button__text").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));

    }

}