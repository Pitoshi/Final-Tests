package POM.page;

import POM.elements.HeaderElement;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutPage {

    public final HeaderElement header;


    public CheckoutPage() {
        this.header = new HeaderElement();
    }

    private SelenideElement firstNameLocator = $("#first-name");
    private SelenideElement lastNameLocator = $("#last-name");
    private SelenideElement poctalCodeLocator = $("#postal-code");
    private SelenideElement continueButtonLocator = $("#continue");
    private SelenideElement totalPriceLocator = $(".summary_total_label");
    private SelenideElement finishButtonLocator = $("#finish");



    @Step("Заполняем форму оплаты {firstName}, {lastName}, {postalCode}")
    public void fillOutCheckoutForm(String firstName, String lastName, String postalCode) {
        firstNameLocator.setValue(firstName);
        lastNameLocator.setValue(lastName);
        poctalCodeLocator.setValue(postalCode);
        continueButtonLocator.submit();
    }

    @Step("Проверяем правильность итоговой стоимости Total: $58.29")
    public void checkOfTotalPrice() {
        assertEquals("Total: $58.29", totalPriceLocator.text());
    }

    @Step("Нажимаем завершение оплаты")
    public void finish() {
        finishButtonLocator.click();
    }

}
