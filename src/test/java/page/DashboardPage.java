package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {

    private SelenideElement heading = $x("//h2");

    public DashboardPage() {
        heading.should(Condition.visible);
    }
}
