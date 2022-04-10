package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    public DashboardPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
        SelenideElement title = $("h1.heading");
        title.shouldHave(text("Ваши карты"));
    }

    private int extractBalance(String cardInfo) {
        String balanceStart = "баланс:";
        String balanceFinish = "р.";
        var value = cardInfo.substring
                        (cardInfo.indexOf(balanceStart) + balanceStart.length(),
                                cardInfo.indexOf(balanceFinish))
                .trim();
        return Integer.parseInt(value);
    }

    public int getCardBalance(DataHelper.CardId cardId) {
        return extractBalance($("[data-test-id='" + cardId.getId() + "']").getText());
    }

    public ReplenishmentPage transfer(DataHelper.CardId cardId) {
        $("[data-test-id='" + cardId.getId() + "'] [data-test-id=action-deposit]").click();
        return new ReplenishmentPage();
    }
}