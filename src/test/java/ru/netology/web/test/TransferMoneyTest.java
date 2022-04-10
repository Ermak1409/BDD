package ru.netology.web.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferMoneyTest {
    @BeforeAll
    public static void loginToPersonalAccount() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getUserAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor();
        verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    public void cardBalancing() {

        var dashboardPage = new DashboardPage();
        var firstCardId = DataHelper.getFirstCardId();
        var balanceFirstCard = dashboardPage.getCardBalance(firstCardId);
        var secondCardId = DataHelper.getSecondCardId();
        var balanceSecondCard = dashboardPage.getCardBalance(secondCardId);

        int amountTransfer;
        if (balanceFirstCard > balanceSecondCard) {
            amountTransfer = (balanceFirstCard - balanceSecondCard) / 2;
            var replenishmentPage = dashboardPage.transfer(secondCardId);
            var transferInfo = DataHelper.setSecondCardTransferInfo(amountTransfer);

            replenishmentPage.transferBetweenOwnCards(transferInfo);
        }
        if (balanceFirstCard < balanceSecondCard) {
            amountTransfer = (balanceSecondCard - balanceFirstCard) / 2;
            var replenishmentPage = dashboardPage.transfer(firstCardId);
            var transferInfo = DataHelper.setFirstCardTransferInfo(amountTransfer);

            replenishmentPage.transferBetweenOwnCards(transferInfo);
        }
    }


    @Test
    public void shouldTransferFromSecond() {

        var dashboardPage = new DashboardPage();
        var firstCardId = DataHelper.getFirstCardId();
        var initialBalanceFirstCard = dashboardPage.getCardBalance(firstCardId);
        var secondCardId = DataHelper.getSecondCardId();
        var initialBalanceSecondCard = dashboardPage.getCardBalance(secondCardId);
        var replenishmentPage = dashboardPage.transfer(firstCardId);
        var transferInfo = DataHelper.getFirstCardTransferInfo();
        //Осуществление перевода денег:
        replenishmentPage.transferBetweenOwnCards(transferInfo);
        //Получение итогового баланса по обеим картам:
        var finalBalanceFirstCard = dashboardPage.getCardBalance(firstCardId);
        var finalBalanceSecondCard = dashboardPage.getCardBalance(secondCardId);
        //Проверка зачисления на первую карту:
        assertEquals(transferInfo.getAmount(), finalBalanceFirstCard - initialBalanceFirstCard);
        //Проверка списания со второй карты:
        assertEquals(transferInfo.getAmount(), initialBalanceSecondCard - finalBalanceSecondCard);
    }

    @Test   //Перевод с первой карты на вторую
    @DisplayName("Transfer money from the first card to the second card")
    public void shouldTransferFromFirst() {
        var dashboardPage = new DashboardPage();
        var firstCardId = DataHelper.getFirstCardId();
        var initialBalanceFirstCard = dashboardPage.getCardBalance(firstCardId);
        var secondCardId = DataHelper.getSecondCardId();
        var initialBalanceSecondCard = dashboardPage.getCardBalance(secondCardId);
        var replenishmentPage = dashboardPage.transfer(secondCardId);
        var transferInfo = DataHelper.getSecondCardTransferInfo();
        //Осуществление перевода денег:
        replenishmentPage.transferBetweenOwnCards(transferInfo);
        //Получение итогового баланса по обеим картам:
        var finalBalanceFirstCard = dashboardPage.getCardBalance(firstCardId);
        var finalBalanceSecondCard = dashboardPage.getCardBalance(secondCardId);
        //Проверка списания с первой карты:
        assertEquals(transferInfo.getAmount(), initialBalanceFirstCard - finalBalanceFirstCard);
        //Проверка зачисления на вторую карту:
        assertEquals(transferInfo.getAmount(), finalBalanceSecondCard - initialBalanceSecondCard);
    }


}
