package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $(byText("Пополнение карты"));
    private SelenideElement transfer = $("[data-test-id='amount'] input");
    private SelenideElement fromCard = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");


    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage transfer(DataHelper.TransferPage transferPage) {
        transfer.setValue(String.valueOf(transferPage.getTransfer()));
        fromCard.setValue(transferPage.getCard());
        transferButton.click();
        return new DashboardPage();
    }
}
