import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryNegativeTests {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    // 1. Отправка заявки с пустым полем "Выберите ваш город";
    @Test
    public void shouldReturnErrorMessageIfCityEmpty() {

        $("[data-test-id='city'] input").setValue("");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Михаил Мамин-Сибиряк");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Поле обязательно для заполнения"));

    }

    // 2. Отправка заявки с городом не из административных центров субъектов РФ;
    @Test
    public void shouldReturnErrorMessageIfCityInvalid() {


        $("[data-test-id='city'] input").setValue("Сингапур");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Михаил Мамин-Сибиряк");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    // 3. Отправка заявки с пустым полем "Дата встречи";
    @Test
    public void shouldReturnErrorMessageIfDateIsEmpty() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue("");
        $("[data-test-id='name'] input").setValue("Михаил Мамин-Сибиряк");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible).
                shouldHave(exactText("Неверно введена дата"));
    }

    // 4. Отправка заявки с пустым полем "Фамилия и Имя";
    @Test
    public void shouldReturnErrorMessageIfSurnameAndNameIsEmpty() {


        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    // 5. Отправка заявки с полем "Фамилия и Имя" на английском языке;
    @Test
    public void shouldReturnErrorMessageIfInvalidSurnameAndName() {


        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Maria Novikova");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // 6. Отправка заявки с полем "Фамилия и Имя", состоящий из цифр;
    @Test
    public void shouldReturnErrorMessageIfSurnameAndNameConsistsOfNumbers() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("15790");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // 7. Отправка заявки с полем "Фамилия и Имя", состоящий из спецсимволов;
    @Test
    public void shouldReturnErrorMessageIfSurnameAndNameConsistsOfSpecialCharacters() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("!№%?@");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // 8. Отправка заявки с пустым полем "Мобильный телефон";
    @Test
    public void shouldReturnSuccessIfFieldsAreFilledInCorrectly() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Мария Новикова");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    // 9. Отправка заявки с введенным в поле "Мобильный телефон" букв;
    @Test
    public void shouldReturnErrorMessageIfPhoneWithLetters() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Мария Новикова");
        $("[data-test-id='phone'] input").setValue("СОЛО");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // 10. Отправка заявки с введенным в поле "Мобильный телефон" спецсимволов;
    @Test
    public void shouldReturnErrorMessageIfPhoneHasSpecialCharacters() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Мария Новикова");
        $("[data-test-id='phone'] input").setValue("!№%?@");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // 11. Отправка заявки с неверно заполненным полем "Мобильный телефон", без "+";
    @Test
    public void shouldReturnErrorMessageIfPhoneIsWrong() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Мария Новикова");
        $("[data-test-id='phone'] input").setValue("79263456789");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // 12. Отправка заявки с введенным в поле "Мобильный телефон" неверного количества цифр;
    @Test
    public void shouldReturnErrorMessageIfPhoneWrong() {

        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Мария Новикова");
        $("[data-test-id='phone'] input").setValue("7926345678953");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // 13. Отправка заявки без согласия с условиями обработки персональных данных;
    @Test
    public void shouldReturnErrorMessageIfDoNotTick() {


        $("[data-test-id='city'] input").setValue("Уфа");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Мария Новикова");
        $("[data-test-id='phone'] input").setValue("+79263456789");
        $("[role=button] .button__content").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(visible)
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
