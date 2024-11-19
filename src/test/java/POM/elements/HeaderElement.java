package POM.elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class HeaderElement {


    private final SelenideElement cartLocator = $(".shopping_cart_link");

    @Step("Переходим в корзину")
    public void goToCart() {
        cartLocator.click();
    }

    public void logout() {
    }
}
