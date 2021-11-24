package ru.netology;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    private int transfer;

    @Test
    void shouldTransferMoneyBetweenOwnCardsFromOne() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var balanceFirstCardBeforeTransfer = DashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var balanceSecondCardBeforeTransfer = DashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        transfer = 200;
        var dashboardTransfer = DataHelper.getTransfer(authInfo, transfer, DataHelper.getSecondCard());
        $$("[data-test-id='action-deposit']").first().click();
        dashboardPage.transfer(dashboardTransfer);
        var balanceFirstCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var balanceSecondCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        assertEquals(balanceFirstCardBeforeTransfer + transfer, balanceFirstCardAfterTransfer);
        assertEquals(balanceSecondCardBeforeTransfer - transfer, balanceSecondCardAfterTransfer);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsFromTwo() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var balanceFirstCardBeforeTransfer = DashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var balanceSecondCardBeforeTransfer = DashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        transfer = 200;
        var dashboardTransfer = DataHelper.getTransfer(authInfo, transfer, DataHelper.getFirstCard());
        $$("[data-test-id='action-deposit']").last().click();
        dashboardPage.transfer(dashboardTransfer);
        var balanceFirstCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var balanceSecondCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        assertEquals(balanceFirstCardBeforeTransfer - transfer, balanceFirstCardAfterTransfer);
        assertEquals(balanceSecondCardBeforeTransfer + transfer, balanceSecondCardAfterTransfer);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsMoreThenBalance() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var balanceFirstCardBeforeTransfer = DashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var balanceSecondCardBeforeTransfer = DashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        transfer = 10100;
        var dashboardTransfer = DataHelper.getTransfer(authInfo, transfer, DataHelper.getSecondCard());
        $$("[data-test-id='action-deposit']").first().click();
        dashboardPage.transfer(dashboardTransfer);
        var balanceFirstCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var balanceSecondCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        assertEquals(balanceFirstCardBeforeTransfer + transfer, balanceFirstCardAfterTransfer);
        assertEquals(balanceSecondCardBeforeTransfer - transfer, balanceSecondCardAfterTransfer);
    }


}
