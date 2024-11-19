package UITests;

import POM.page.*;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import static UITestData.GetData.getData;
import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static properties.GetProperties.getProperty;

public class SwagLabsTests {
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CompletePage completePage;

    private final String standardUser = getProperty("standardUser");
    private final String invalidPassword = getProperty ("invalidPassword");
    private final String blockedUser = getProperty("blockedUser");
    private final String performanceGlitchUser = getProperty("performanceGlitchUser");
    private final String password = getProperty("passwordUI");


    @BeforeAll
    static void configure() {
        Configuration.baseUrl = getProperty("urlUI");
        Configuration.headless = true;
    }

    @BeforeEach
    void initPages() {
        loginPage = new LoginPage();
        productsPage = new ProductsPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        completePage = new CompletePage();
    }

    @Test
    @DisplayName("Успешная авторизация")
    @Tag("positive")
    void loginSuccessfully() {
        loginPage.login(standardUser, password);
        productsPage.checkOfSuccessfulLogin();
    }

    @Test
    @DisplayName("Авторизация заблокированного пользователя")
    @Tag("negative")
    void loginBlocked() {
        loginPage.login(blockedUser, password);
        step("Проверяем что вышло сообщение о том, что пользователь заблокирован", () ->
                assertEquals("Epic sadface: Sorry, this user has been locked out.", loginPage.getErrorText())
        );
    }

    @Test
    @DisplayName("e2e-сценарий под пользователем standard_user")
    @Tag("positive")
     void standardUserE2E() {
        loginPage.login(standardUser, password);
        productsPage.addToCart();
        productsPage.header.goToCart();
        cartPage.checkItemsInCartNumberShouldBe(3);
        cartPage.checkout();
        checkoutPage.fillOutCheckoutForm(
                getData("firstName"),
                getData("lastName"),
                getData("zip")
        );
        checkoutPage.checkOfTotalPrice();
        checkoutPage.finish();
        completePage.checkComplete();
    }

    @Test
    @DisplayName("e2e-сценарий под пользователем performance_glitch_user")
    @Tag("positive")
    void performanceGlitchUserE2E() {
        loginPage.login(performanceGlitchUser, password);
        productsPage.checkOfSuccessfulLogin();
        productsPage.addToCart();
        productsPage.header.goToCart();
        cartPage.checkItemsInCartNumberShouldBe(3);
        cartPage.checkout();
        checkoutPage.fillOutCheckoutForm(
                getData("firstName"),
                getData("lastName"),
                getData("zip")
        );
        checkoutPage.checkOfTotalPrice();
        checkoutPage.finish();
        completePage.checkComplete();
    }

    @Test
    @DisplayName("Попытка входа с неверным паролем")
    @Tag("negative")
    void loginWithInvalidPassword() {
        loginPage.login(standardUser, invalidPassword);
        step("Проверяем сообщение об ошибке при неверном пароле", () ->
                assertEquals("Epic sadface: Username and password do not match any user in this service",
                        loginPage.getErrorText())
        );
    }

    @Test
    @DisplayName("Проверка добавления одного товара в корзину")
    @Tag("positive")
    void addSingleItemToCart() {
        loginPage.login(standardUser, password);
        productsPage.addSingleItemToCart();
        productsPage.header.goToCart();
        cartPage.checkItemsInCartNumberShouldBe(1);
    }

    @Test
    @DisplayName("Проверка удаления товара из корзины")
    @Tag("positive")
    void removeItemFromCart() {
        loginPage.login(standardUser, password);
        productsPage.addSingleItemToCart();
        productsPage.header.goToCart();
        cartPage.removeItem();
        cartPage.checkItemsInCartNumberShouldBe(0);
    }

    @Test
    @DisplayName("Проверка оформления заказа без заполнения обязательных полей")
    @Tag("negative")
    void checkoutWithEmptyFields() {
        loginPage.login(standardUser, password);
        productsPage.addToCart();
        productsPage.header.goToCart();
        cartPage.checkout();
        checkoutPage.fillOutCheckoutForm("", "", "");
        step("Проверяем сообщение об ошибке при пустых полях", () ->
                assertEquals("Error: First Name is required", checkoutPage.getErrorMessage())
        );
    }

    @Test
    @DisplayName("Проверка сортировки товаров по цене")
    @Tag("positive")
    void sortProductsByPrice() {
        loginPage.login(standardUser, password);
        productsPage.sortByPrice();
        step("Проверяем корректность сортировки по цене", () ->
                assertTrue(productsPage.isPriceSortedCorrectly())
        );
    }

    @Test
    @DisplayName("Проверка выхода из системы")
    @Tag("positive")
    void logoutTest() {
        loginPage.login(standardUser, password);
        productsPage.header.logout();
        step("Проверяем, что произошел выход из системы", () ->
                assertTrue(loginPage.isLoginPageDisplayed())
        );
    }

    @Test
    @DisplayName("Проверка корзины после выхода из системы")
    @Tag("positive")
    void cartClearAfterLogout() {
        loginPage.login(standardUser, password);
        productsPage.addToCart();
        productsPage.header.logout();
        loginPage.login(standardUser, password);
        step("Проверяем, что корзина пуста после повторного входа", () ->
                assertEquals(0, productsPage.getCartItemsCount())
        );
    }

    @Test
    @DisplayName("Проверка деталей товара")
    @Tag("positive")
    void productDetailsTest() {
        loginPage.login(standardUser, password);
        productsPage.openProductDetails(0);
        step("Проверяем наличие описания товара", () ->
                assertTrue(productsPage.isProductDescriptionDisplayed())
        );
    }

    @Test
    @DisplayName("Проверка максимального количества товаров в корзине")
    @Tag("positive")
    void maxItemsInCartTest() {
        loginPage.login(standardUser, password);
        productsPage.addAllItemsToCart();
        step("Проверяем максимальное количество товаров в корзине", () ->
                assertEquals(6, productsPage.getCartItemsCount())
        );
    }
}
