package data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {

    private static Faker faker = new Faker();

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private final String login;
        private final String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    @Value
    public static class VerificationCode {
        private final String code;
    }

    public static VerificationCode getVerificationCode(String login) {
        return new VerificationCode(SQLHelper.getCode(login));
    }

    public static VerificationCode getRandomCode() {
        return new VerificationCode(String.valueOf(faker.number().numberBetween(100_000, 999_999)));
    }

}
