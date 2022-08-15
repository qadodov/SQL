package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$x;

public class AuthorizationPage {

    private SelenideElement loginField = $x("//*[@data-test-id=\"login\"]//input");

    private SelenideElement passwordField = $x("//*[@data-test-id=\"password\"]//input");

    private SelenideElement confirmButton = $x("//*[@data-test-id=\"action-login\"]");

    private SelenideElement errorWrongPasswordOrLogin = $x("//*[@data-test-id=\"error-notification\"]/div[@class=\"notification__content\"]");

    private SelenideElement errorUserBlocked = $x("//*[@data-test-id=\"error-notification\"]/div[@class=\"notification__content\"]");

    public AuthorizationPage() {
        loginField.should(Condition.visible);
        passwordField.should(Condition.visible);
        confirmButton.should(Condition.visible);
    }

    public VerificationPage validVerify(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        confirmButton.click();
        return new VerificationPage();
    }

    public void fillFieldsAndSendForm(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        confirmButton.click();
    }

    public void failWrongPasswordOrLogin() {
        errorWrongPasswordOrLogin.should(Condition.visible);
        errorWrongPasswordOrLogin.shouldHave(Condition.text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    public void failUserBlocked() {
        errorUserBlocked.should(Condition.visible);
        errorUserBlocked.shouldHave(Condition.text("Ошибка! " + "Пользователь заблокирован"));
    }
}
