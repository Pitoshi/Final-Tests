package POM.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;


public class LoginPage {

    private SelenideElement userNameInputLocator = $("#user-name");
    private SelenideElement passwordInputLocator = $("#password");
    private SelenideElement loginButtonLocator = $("#login-button");
    private SelenideElement errorTextLocator = $("[data-test='error']");

    @Step("Логин под пользователем {user}")
    public void login(String user, String password) {
        Selenide.open("/");
        userNameInputLocator.setValue(user);
        passwordInputLocator.setValue(password);
        loginButtonLocator.click();
    }

    public String getErrorText() {
        return errorTextLocator.text();

    }

}
