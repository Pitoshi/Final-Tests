package POM.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompletePage {

    private SelenideElement finishButtonLocator = $(".complete-header");

    @Step("Проверям, что оформление заказа успешно завершено")
    public void checkComplete() {
        assertEquals("Thank you for your order!", finishButtonLocator.text());

    }

}
