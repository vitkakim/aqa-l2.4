package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $(byText("Ваши карты"));
    private static ElementsCollection cards = $$(".list__item [data-test-id]");
    private static final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";
    private ElementsCollection depositButton = $$("[data-test-id='action-deposit']");

    private static int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public static int getCardBalance(String id) {
        val text = cards.find(attribute("data-test-id", id)).text();
        return extractBalance(text);
    }

    public TransferPage firstDepositButton() {
        depositButton.first().click();
        return new TransferPage();
    }

    public TransferPage secondDepositButton() {
        depositButton.last().click();
        return new TransferPage();
    }

}
