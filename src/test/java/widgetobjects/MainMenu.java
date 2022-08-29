package widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainMenu {
    SelenideElement menuProducts = $(byText("Меню"));
    SelenideElement pizzaCatalog = $(byText("Пицца"));
    SelenideElement menuJump = $(".store-menu");
    SelenideElement firstProductItem = $$("img").get(5);

    @Step("Перейти в каталог Пиццы")
    public void goToPizzasCatalog() {
        menuProducts.hover();
        pizzaCatalog.click();
    }

    @Step("Перейти в раздел {section}")
    public void goToSection(String section) {
        menuJump.$(byText(section)).click();
    }

    @Step("Перейти в первый товар на странице")
    public void goToFirstProduct(){
        firstProductItem.click();
    }
}
