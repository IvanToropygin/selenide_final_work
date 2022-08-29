package widgetobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Cart {
    public SelenideElement firstProduct = $$(".product-name").get(1);
    public SelenideElement secondProduct = $$(".product-name").get(2);
    public SelenideElement quantityFirstProduct = $$(".quantity .input-text").get(0);
    public SelenideElement quantitySecondProduct = $$(".quantity .input-text").get(1);
    public SelenideElement totalPriceLocator = $(".order-total bdi");
    public SelenideElement wrongCouponMessage = $(withText("Неверный купон."));
    public SelenideElement couponWasAppliedMessage = $(withText("Coupon code applied successfully."));
    ElementsCollection quantityfield = $$(".quantity .input-text");
    SelenideElement updateCartBtn = $("[name ='update_cart']");
    SelenideElement couponCodeInput = $("#coupon_code");
    SelenideElement applyCouponBtn = $("[name ='apply_coupon']");

    @Step("Получить итоговую стоимость")
    public double getTotalPrice() {
        return Double.parseDouble(totalPriceLocator.getText().replace("₽", "").replace(',', '.'));
    }

    @Step("Установить количество товара: {product} - {quantity} шт.")
    public void setQuantityfield(int product, String quantity) {
        quantityfield.get(product).setValue(quantity);
    }

    @Step("Обновить корзину")
    public void updateCart() {
        updateCartBtn.click();
    }

    @Step("Применить купон: {coupon}")
    public void applyCoupon(String coupon){
        couponCodeInput.setValue(coupon);
        applyCouponBtn.click();
    }
}
