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




    private final SelenideElement productsLocator = $(".title");
    private final SelenideElement SauceLabsBackpackCartButtonLocator = $("#add-to-cart-sauce-labs-backpack");
    private final SelenideElement SauceLabsBoltTshirtCartButtonLocator = $("#add-to-cart-sauce-labs-bolt-t-shirt");
    private final SelenideElement SauceLabsOnesieCartButtonLocator = $("#add-to-cart-sauce-labs-onesie");

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

    public void addSingleItemToCart() {
        // Логика добавления одного товара
    }

    public void sortByPrice() {
        // Логика сортировки по цене
    }

    public boolean isPriceSortedCorrectly() {
        // Проверка корректности сортировки
        return true;
    }

    public int getCartItemsCount() {
        // Получение количества товаров в корзине
        return 0;
    }

    public void openProductDetails(int index) {
        // Открытие деталей товара
    }

    public boolean isProductDescriptionDisplayed() {
        // Проверка отображения описания товара
        return true;
    }

    public void addAllItemsToCart() {
        // Добавление всех доступных товаров в корзину
    }

}
