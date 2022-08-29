package widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class RegisterForm {
    SelenideElement usernameInput = $("#reg_username");
    SelenideElement emailInput = $("#reg_email");
    SelenideElement passwordInput = $("#reg_password");
    SelenideElement registerBtn = $(".woocommerce-form-register__submit");

    @Step("Зарегистрироваться")
    public void registration(String username, String email, String password) {
        usernameInput.setValue(username);
        emailInput.setValue(email);
        passwordInput.setValue(password);
        registerBtn.click();
    }
}
