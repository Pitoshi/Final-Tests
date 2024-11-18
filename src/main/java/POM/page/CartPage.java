package POM.page;

import POM.elements.HeaderElement;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartPage {

    public final HeaderElement header;

    public CartPage() {
        this.header = new HeaderElement();
    }

    private ElementsCollection cartItemsLocator = $$(".inventory_item_name");
    private SelenideElement checkoutButtonLocator = $("#checkout");
    private SelenideElement cartItemsListLocator = $(".cart_list");

    public int countItemsInCart() {
        cartItemsLocator.shouldHave(size(3));
        return cartItemsLocator.size();
    }

    @Step("Проверяем, что в корзине лежит {x} товара")
    public void checkItemsInCartNumberShouldBe(int x) {
        takeScreen(cartItemsListLocator);
        assertEquals(x, countItemsInCart());
    }

    @Attachment(type = "image/png", value = "cart", fileExtension = ".png")
    private byte[] takeScreen(SelenideElement element) {
        return element.getScreenshotAs(OutputType.BYTES);
    }

    @Step("Переходим к оплате")
    public void checkout() {
        checkoutButtonLocator.click();

    }
}
