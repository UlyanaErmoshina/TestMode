package ru.netology;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class AuthTest {
    private DataGenerator dataGenerator;

    private static Faker faker =new Faker(new Locale("en"));
    private static DataClient user = new DataClient("vasya", "password", "active");


    public static String generatePassword() {
        return  faker.random().hex(8);
    }

    public static String generateLogin() {
        return  faker.name().firstName();
    }

    @Test
    public void getValidUser(){
        dataGenerator.generateUser(new DataClient(generateLogin(), "generatePassword", "active"));
    }

    @Test
    public void getUserWithInvalidLogin(){
        dataGenerator.generateUser(new DataClient("", generatePassword(), "active"));
    }

    @Test
    public void getUserWithInvalidPassword(){
        dataGenerator.generateUser(new DataClient(generateLogin(), "", "active"));
    }

    @Test
    public void getUserWithIncorrectPassword(){
        dataGenerator.generateUser(new DataClient(user.getLogin(), generatePassword(), "active"));
    }

    @Test
    public void getUserWithIncorrectLogin(){
        dataGenerator.generateUser(new DataClient(generateLogin(), user.getPassword(), "active"));
    }

    @Test
    public void getLockedUser(){
        dataGenerator.generateUser(new DataClient(user.getLogin(), user.getPassword(), "blocked"));
    }


}