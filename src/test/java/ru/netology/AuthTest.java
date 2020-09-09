package ru.netology;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.*;


class AuthTest {

    @Test
    void shouldLoginValidUser() {
        DataClient validUser = generateValidUser();
        open("http://localhost:7777");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(validUser.getLogin());
        form.$("[data-test-id=password] input").setValue(validUser.getPassword());
        form.$(".button").click();
        $(".App_appContainer__3jRx1").shouldHave(text("Личный кабинет"));

    }

    @Test
    void shouldLoginUserWithInvalidLogin() {
        DataClient invalidLoginClient = getUserWithInvalidLogin();
        open("http://localhost:7777");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(invalidLoginClient.getLogin());
        form.$("[data-test-id=password] input").setValue(invalidLoginClient.getPassword());
        form.$(".button").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void shouldLoginUserWithInvalidPassword() {
        DataClient invalidPasswordClient = getUserWithInvalidPassword();
        open("http://localhost:7777");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(invalidPasswordClient.getLogin());
        form.$("[data-test-id=password] input").setValue(invalidPasswordClient.getPassword());
        form.$(".button").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldLoginLockedUser() {
        DataClient lockedUser = getLockedUser();
        open("http://localhost:7777");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(lockedUser.getLogin());
        form.$("[data-test-id=password] input").setValue(lockedUser.getPassword());
        form.$(".button").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }
}

