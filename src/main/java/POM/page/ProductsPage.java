package POM.page;

import POM.elements.HeaderElement;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductsPage {

    public final HeaderElement header;

    public ProductsPage() {
        this.header = new HeaderElement();
    }


    private SelenideElement productsLocator = $(".title");
    private SelenideElement SauceLabsBackpackCartButtonLocator = $("#add-to-cart-sauce-labs-backpack");
    private SelenideElement SauceLabsBoltTshirtCartButtonLocator = $("#add-to-cart-sauce-labs-bolt-t-shirt");
    private SelenideElement SauceLabsOnesieCartButtonLocator = $("#add-to-cart-sauce-labs-onesie");

    public String getProductsText() {
        return productsLocator.text();

    }

    @Step("Проверяем что перешли на страницу магазина, тем что нашли элемент Products")
    public void checkOfSuccessfulLogin() {
        assertEquals("Products", getProductsText());
    }

    @Step("Добавляем товары в корзину")
    public void addToCart() {
        SauceLabsBackpackCartButtonLocator.click();
        SauceLabsBoltTshirtCartButtonLocator.click();
        SauceLabsOnesieCartButtonLocator.click();


    }
}
