package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {

    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement fromField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");

    public ReplenishmentPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
        SelenideElement title = $("h1.heading");
        title.shouldHave(text("Пополнение карты"));
    }

    private void fieldClearing() {
        amountField.sendKeys(Keys.CONTROL + "a");
        amountField.sendKeys(Keys.DELETE);
        fromField.sendKeys(Keys.CONTROL + "a");
        fromField.sendKeys(Keys.DELETE);
    }

    public void transferBetweenOwnCards(DataHelper.TransferInfo transferInfo) {
        fieldClearing();
        amountField.setValue(String.valueOf(transferInfo.getAmount()));
        fromField.setValue(transferInfo.getNumberCard());
        transferButton.click();
        if (errorNotification.is(visible)) {
            cancelButton.click();
        }
        new DashboardPage();
    }
}
