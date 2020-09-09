package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.http.HttpResponse;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static Faker faker = new Faker(new Locale("en"));

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(7777)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    public static void registerUser(DataClient user) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
        .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
        .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK

    }

    public static String generatePassword() {
        return  faker.random().hex(8);
    }
    public static String generateLogin() {
        return  faker.name().firstName();
    }

    public static DataClient generateValidUser(){
        DataClient client = new DataClient(generateLogin(),generatePassword(),"active");
        registerUser(client);
        return client;

    }
    public static DataClient getUserWithInvalidLogin(){
        String password = generatePassword();
        registerUser(new DataClient("valid"+generateLogin(),password,"active"));
        return new DataClient("invalid"+generateLogin(),password,"active");

    }
    public static DataClient getUserWithInvalidPassword(){
        String login = generateLogin();
        registerUser(new DataClient(login,"valid"+generatePassword(),"active"));
        return new DataClient(login,"invalid"+generatePassword(),"active");

    }
    public static DataClient getLockedUser(){
        DataClient client = new DataClient(generateLogin(),generatePassword(),"blocked");
        registerUser(client);
        return client;

    }

}
