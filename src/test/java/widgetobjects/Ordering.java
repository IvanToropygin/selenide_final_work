package widgetobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class Ordering {
    SelenideElement nameInput = $("#billing_first_name");
    SelenideElement surnameInput = $("#billing_last_name");
    SelenideElement cityInput = $("#billing_city");
    SelenideElement addressInput = $("#billing_address_1");
    SelenideElement regionInput = $("#billing_state");
    SelenideElement indexInput = $("#billing_postcode");
    SelenideElement phoneInput = $("#billing_phone");
    SelenideElement deliveryDateInput = $("#order_date");
    SelenideElement payOnDelivery = $(withText("Оплата при доставке"));
    SelenideElement consentDataProcessing = $(".woocommerce-terms-and-conditions-checkbox-text");
    SelenideElement placeOrderBtn = $("#place_order");

    @Step("Заполнить данные для оформления заказа")
    public void fillOrderingFields(String name, String surname, String city, String address, String region, String index,
                                   String phoneNumber) {
        nameInput.setValue(name);
        surnameInput.setValue(surname);
        cityInput.setValue(city);
        addressInput.setValue(address);
        regionInput.setValue(region);
        indexInput.setValue(index);
        phoneInput.setValue(phoneNumber);
    }

    @Step("Установить дату доставки {date}")
    public void setDeliveryDate(String date) {
        deliveryDateInput.setValue(date);
    }

    @Step("Выбрать оплату при получении")
    public void choosePayOnDelivery() {
        payOnDelivery.click();
    }
    @Step("Cогласиться на обработку данных")
    public void setConsentDataProcessing() {
        consentDataProcessing.click();
    }
    @Step("Нажать на кнопку 'Оформить заказ'")
    public void placeOrder() {
        placeOrderBtn.click();
    }
}
