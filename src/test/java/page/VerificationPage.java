package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class VerificationPage {

    private SelenideElement codeField = $x("//*[@data-test-id=\"code\"]//input");

    private SelenideElement verifyButton = $x("//*[@data-test-id=\"action-verify\"]");

    private SelenideElement errorWrongCode = $x("//*[@data-test-id=\"error-notification\"]/div[@class=\"notification__content\"]");

    public VerificationPage() {
        codeField.should(Condition.visible);
        verifyButton.should(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        errorWrongCode.should(Condition.visible);
        errorWrongCode.should(text("Ошибка! " + "Неверно указан код! Попробуйте ещё раз."));
    }
}
