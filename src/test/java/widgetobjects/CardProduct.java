package widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardProduct {

    SelenideElement addToCartBtn = $(".single_add_to_cart_button");
    SelenideElement price = $$("bdi").first();

    @Step("Добавить в корзину")
    public void addToCart() {
        addToCartBtn.click();
        double pizzaPrice = Double.parseDouble(price.getText().replace("₽", "").replace(',', '.'));
        HeaderBlock.amountInCartExpected += pizzaPrice;
    }
}
