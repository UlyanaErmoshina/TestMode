package ru.netology;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.*;


class AuthTest {

    DataGenerator data = new DataGenerator();

    DataClient invalidPasswordClient = getUserWithInvalidPassword();
    DataClient invalidLoginClient = getUserWithInvalidLogin();
    //DataClient validUser = generateValidUser();
    DataClient lockedUser = getLockedUser();

    @Test
    void shouldLoginValidUser() {
        DataClient validUser = generateValidUser();
        open("http://localhost:7777");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(validUser.getLogin());
        form.$("[data-test-id=password] input").setValue(data.generateValidUser().getPassword());
        form.$(".button").click();
        $(".App_appContainer").shouldHave(text("Личный кабинет"));

    }

    @Test
    void shouldLoginUserWithInvalidLogin() {
        open("http://localhost:7777");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(data.getUserWithInvalidLogin().getLogin());
        form.$("[data-test-id=password] input").setValue(data.getUserWithInvalidLogin().getPassword());
        form.$(".button").click();
        $("[data-test-id=success-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void shouldLoginUserWithInvalidPassword() {
        open("http://localhost:7777");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(data.getUserWithInvalidPassword().getLogin());
        form.$("[data-test-id=password] input").setValue(data.getUserWithInvalidPassword().getPassword());
        form.$(".button").click();
        $("[data-test-id=success-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldLoginLockedUser() {
        open("http://localhost:7777");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(data.getLockedUser().getLogin());
        form.$("[data-test-id=password] input").setValue(data.getLockedUser().getPassword());
        form.$(".button").click();
    }
}

