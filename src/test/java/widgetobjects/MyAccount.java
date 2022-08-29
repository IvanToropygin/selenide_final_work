package widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class MyAccount {
    SelenideElement registerBtn = $(".custom-register-button");

    @Step("Нажать на кнопку 'Зарегистрироваться'")
    public void RegisterBtnPress() {
        registerBtn.click();
    }
}
