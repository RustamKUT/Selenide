import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryPositiveTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    // 1. Отправка заявки с верно заполненными полями;
    @Test
    public void shouldReturnSuccessIfFieldsAreFilledInCorrectly() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Мария Новикова");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }

    // 2. Отправка заявки с указанием фамилии через дефис;
    @Test
    public void shouldReturnSuccessfullyIfSurnameWithHyphen() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Михаил Мамин-Сибиряк");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));

    }

    // 3. Отправка заявки с указанием города через дефис;
    @Test
    public void shouldReturnSuccessfullyIfCityWithHyphen() {

        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Михаил Мамин-Сибиряк");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));


    }
}
