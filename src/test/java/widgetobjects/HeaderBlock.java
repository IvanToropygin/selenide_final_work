package widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class HeaderBlock {
    public static double amountInCartExpected = 0;

    SelenideElement amountCartInHeader = $(".wcmenucart-contents");
    SelenideElement searchField = $(".search-field");
    SelenideElement magnifyingGlassBtn = $(".fa-search");
    SelenideElement cartItem = $(".fa-shopping-cart");
    public SelenideElement nameInHeader = $(".user-name");

    @Step("Найти в сторке поиска товар {product}")
    public void findProduct(String product) {
        searchField.setValue(product);
        magnifyingGlassBtn.click();
    }

    @Step("Почучить стоимость товаров в Хэдере")
    public double getAmountCartInHeader() {
        return Double.parseDouble(amountCartInHeader.getText()
                .replace("₽", "")
                .replace(',', '.')
                .replace("[", "")
                .replace("]", ""));
    }

    @Step("Перейти в корзину")
    public void goToCart() {
        cartItem.click();
    }
}
