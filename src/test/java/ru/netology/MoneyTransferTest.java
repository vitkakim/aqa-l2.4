package ru.netology;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    private int sum;

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
        sum = 200;
        var transferPage = dashboardPage.firstDepositButton();
        transferPage.transfer(DataHelper.getTransfer(authInfo, sum, DataHelper.getSecondCard()));
        var balanceFirstCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var balanceSecondCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        assertEquals(balanceFirstCardBeforeTransfer + sum, balanceFirstCardAfterTransfer);
        assertEquals(balanceSecondCardBeforeTransfer - sum, balanceSecondCardAfterTransfer);
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
        sum = 200;
        var transferPage = dashboardPage.secondDepositButton();
        transferPage.transfer(DataHelper.getTransfer(authInfo, sum, DataHelper.getFirstCard()));
        var balanceFirstCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var balanceSecondCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        assertEquals(balanceFirstCardBeforeTransfer - sum, balanceFirstCardAfterTransfer);
        assertEquals(balanceSecondCardBeforeTransfer + sum, balanceSecondCardAfterTransfer);
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
        sum = 10100;
        var transferPage = dashboardPage.firstDepositButton();
        transferPage.transfer(DataHelper.getTransfer(authInfo, sum, DataHelper.getSecondCard()));
        var balanceFirstCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var balanceSecondCardAfterTransfer = DashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        assertEquals(balanceFirstCardBeforeTransfer, balanceFirstCardAfterTransfer);
        assertEquals(balanceSecondCardBeforeTransfer, balanceSecondCardAfterTransfer);
    }


}
