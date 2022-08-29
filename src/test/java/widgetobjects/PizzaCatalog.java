package widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class PizzaCatalog {
    SelenideElement pizzasList = $("ul");
    SelenideElement pizzaWithHam = $(".wc-products").$(withText("Ветчина"));

    @Step("Открыть карточку пиццы с ветчиной")
    public void openPizzzaWithHamCard() {
        pizzasList.scrollTo();
        pizzaWithHam.click();
    }
}
