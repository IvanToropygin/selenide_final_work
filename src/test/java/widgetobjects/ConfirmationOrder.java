package widgetobjects;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ConfirmationOrder {
    public SelenideElement thankYouTextLocator = $(".woocommerce-thankyou-order-received");
    public SelenideElement deliveryAddressLocator = $("address");
    public SelenideElement deliveryDate = $(".woocommerce-order-overview__date");
    public SelenideElement totalPriceLocator = $$(".woocommerce-Price-amount").last();
}
