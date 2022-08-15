package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.AuthorizationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthTest {

    @BeforeEach
    void setUp() {
//        Configuration.headless = true;
        Configuration.browserSize = "1280x720";
        open("http://localhost:9999");
    }

    @Test
    void shouldAuthorize() {
        var authPage = new AuthorizationPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validVerify(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo.getLogin());
        var dashboardPage = verificationPage.validVerify(verificationCode);

    }

    @Test
    void shouldBlockUserAfterThreeWrongPasswords() {
        String password = DataHelper.getRandomPassword();
        var authPage = new AuthorizationPage();
        var authInfo = DataHelper.getAuthInfo();
        authPage.fillFieldsAndSendForm(authInfo.getLogin(), password);
        authPage.failWrongPasswordOrLogin();
        refresh();

        authPage.fillFieldsAndSendForm(authInfo.getLogin(), password);
        authPage.failWrongPasswordOrLogin();
        refresh();

        authPage.fillFieldsAndSendForm(authInfo.getLogin(), password);
        authPage.failWrongPasswordOrLogin();
        refresh();

        authPage.failUserBlocked();

        assertEquals(SQLHelper.getStatus(authInfo.getLogin()), "blocked");
    }

    @Test
    void shouldNotAuthorizeIfWrongVerificationCodeUsed() {
        var authPage = new AuthorizationPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = authPage.validVerify(authInfo);

        var code = DataHelper.getRandomCode();

        verificationPage.invalidVerify(code);
    }

    @Test
    void shouldNotAuthorizeWithWrongLogin() {
        var authPage = new AuthorizationPage();
        var authInfo = DataHelper.getAuthInfo();
        authPage.fillFieldsAndSendForm(DataHelper.getRandomLogin(), authInfo.getPassword());

        authPage.failWrongPasswordOrLogin();
    }

    @Test
    void shouldNotAuthorizeWithWrongPassword() {
        var authPage = new AuthorizationPage();
        var authInfo = DataHelper.getAuthInfo();
        authPage.fillFieldsAndSendForm(authInfo.getLogin(), DataHelper.getRandomPassword());

        authPage.failWrongPasswordOrLogin();
    }

    @AfterAll
    static void cleanUpDatabase() {
        SQLHelper.cleanDatabase();
    }

}
