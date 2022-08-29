import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import widgetobjects.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты сайта заказа пиццы")
public class WebsiteForOrderingPizzaTests {
    String url = "http://pizzeria.skillbox.cc/";

    @BeforeAll
    @DisplayName("Инициализиуем плагин Allure-Selenide")
    static void init() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterEach
    void tearDown(){
        Selenide.closeWebDriver();
        Selenide.clearBrowserCookies();
    }

    @Test
    @Epic("Поиск, оформление, заказ")
    @DisplayName("Main user scenario")
    @Description("Включает регистрацию, поиск, заказ, увелчение количества, оформление заказа")
    @Issue("spreadsheets/d/1QMVaIgg8KwJKAqGQxQlM8eji_ME6N-SGiCn6_nfjqGU/edit?usp=sharing")
    void basicBuyingScenario() {
        String username = "sel" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
        String email = username + "@ya.ru";
        String password = "selenide123";
        String name = "Петя";
        String surname = "Иванов";
        String city = "Зеленоград";
        String address = "Ленина, д. 1, кв. 15";
        String region = "Московская область";
        String index = "620000";
        String phoneNumber = "+79001233212";
        String tomorrow = LocalDateTime.now().plusDays(1).format((DateTimeFormatter.ofPattern("ddMMyyyy")));
        String deliveryDateExcepted = LocalDateTime.now().plusDays(1).format((DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        open(url);
        MainMenu mainMenu = new MainMenu();
        mainMenu.goToPizzasCatalog();
        new PizzaCatalog().openPizzzaWithHamCard();
        new CardProduct().addToCart();
        HeaderBlock headerBlock = new HeaderBlock();
        headerBlock.findProduct("Какао");
        new CardProduct().addToCart();
        double actualAmountInHeader = headerBlock.getAmountCartInHeader();
        assertEquals(HeaderBlock.amountInCartExpected, actualAmountInHeader);
        headerBlock.goToCart();
        Cart cart = new Cart();
        cart.firstProduct.shouldHave(text("Ветчина"));
        cart.secondProduct.shouldHave(text("Какао"));
        cart.quantityFirstProduct.shouldHave(value("1"));
        cart.quantitySecondProduct.shouldHave(value("1"));
        double totalPrice = cart.getTotalPrice();
        assertEquals(actualAmountInHeader, totalPrice);
        cart.setQuantityfield(0, "2");
        cart.setQuantityfield(1, "2");
        cart.updateCart();
        cart.quantityFirstProduct.shouldHave(value("2"));
        cart.quantitySecondProduct.shouldHave(value("2"));
        cart.totalPriceLocator.shouldHave(text(String.valueOf(totalPrice * 2).replace(".", ",")));
        mainMenu.goToSection("Мой аккаунт");
        new MyAccount().RegisterBtnPress();
        new RegisterForm().registration(username, email, password);
        headerBlock.nameInHeader.shouldHave(textCaseSensitive(username));
        mainMenu.goToSection("Оформление заказа");
        Ordering ordering = new Ordering();
        ordering.fillOrderingFields(name, surname, city, address, region, index, phoneNumber);
        ordering.setDeliveryDate(tomorrow);
        ordering.choosePayOnDelivery();
        ordering.setConsentDataProcessing();
        ordering.placeOrder();
        ConfirmationOrder confirmationOrder = new ConfirmationOrder();
        confirmationOrder.thankYouTextLocator.shouldHave(text("Спасибо! Ваш заказ был получен."));
        confirmationOrder.totalPriceLocator.shouldHave(text(String.valueOf(totalPrice * 2).replace(".", ",")));
        confirmationOrder.deliveryAddressLocator.shouldHave(
                text(name + " " + surname),
                text(address),
                text(city),
                text(region),
                text(index),
                text(phoneNumber),
                text(email)
        );
        confirmationOrder.deliveryDate.shouldHave(text(deliveryDateExcepted));
    }

    @Test
    @Epic("Покупка первого товара на странице")
    @Feature("Применение купонов")
    @Story("Актуальный купон")
    @DisplayName("Supply right coupon")
    @Description("Применение действующего скидочного купона")
    void supplyRightCoupon() {
        String coupon = "GIVEMEHALYAVA";

        open(url);
        new MainMenu().goToFirstProduct();
        new CardProduct().addToCart();
        new HeaderBlock().goToCart();
        Cart cart = new Cart();
        double priceBeforeDiscount = cart.getTotalPrice();
        cart.applyCoupon(coupon);
        cart.totalPriceLocator.shouldHave(text(String.valueOf(priceBeforeDiscount * 0.9).replace(".", ",")));
    }

    @Test
    @Epic("Покупка первого товара на странице")
    @Feature("Применение купонов")
    @Story("Неактуальный купон")
    @DisplayName("Supply wrong coupon")
    @Description("Применение недействующего скидочного купона")
    void supplyWrongCoupon() {
        String coupon = "this is wrong coupon";

        open(url);
        new MainMenu().goToFirstProduct();
        new CardProduct().addToCart();
        new HeaderBlock().goToCart();
        Cart cart = new Cart();
        double priceBeforeDiscount = cart.getTotalPrice();
        cart.applyCoupon(coupon);
        cart.totalPriceLocator.shouldHave(text(String.valueOf(priceBeforeDiscount).replace(".", ",")));
        cart.wrongCouponMessage.shouldBe(visible);
    }

    @Test
    @Epic("Покупка первого товара на странице")
    @Feature("Применение купонов")
    @Story("Повторное применение купона")
    @DisplayName("Double apply coupon")
    @Description("Повторное применение скидочного купона на первый заказ")
    void doubleSupplyCouponForFirstOrder() {
        String coupon = "GIVEMEHALYAVA";
        String username = "sel" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
        String email = username + "@ya.ru";
        String password = "selenide123";
        String name = "Петя";
        String surname = "Иванов";
        String city = "Зеленоград";
        String address = "Ленина, д. 1, кв. 15";
        String region = "Московская область";
        String index = "620000";
        String phoneNumber = "+79001233212";
        String tomorrow = LocalDateTime.now().plusDays(1).format((DateTimeFormatter.ofPattern("ddMMyyyy")));

        open(url);
        MainMenu mainMenu = new MainMenu();
        mainMenu.goToSection("Акции");
        new Promotions().promoCode.shouldBe(visible).shouldHave(text(coupon));
        mainMenu.goToSection("Главная");
        mainMenu.goToFirstProduct();
        new CardProduct().addToCart();
        HeaderBlock headerBlock = new HeaderBlock();
        headerBlock.goToCart();
        Cart cart = new Cart();
        double priceBeforeDiscount = cart.getTotalPrice();
        cart.applyCoupon(coupon);
        cart.totalPriceLocator.shouldHave(text(String.valueOf(priceBeforeDiscount * 0.9).replace(".", ",")));
        mainMenu.goToSection("Мой аккаунт");
        new MyAccount().RegisterBtnPress();
        new RegisterForm().registration(username, email, password);
        new HeaderBlock().nameInHeader.shouldHave(textCaseSensitive(username));
        mainMenu.goToSection("Оформление заказа");
        Ordering ordering = new Ordering();
        ordering.fillOrderingFields(name, surname, city, address, region, index, phoneNumber);
        ordering.setDeliveryDate(tomorrow);
        ordering.choosePayOnDelivery();
        ordering.setConsentDataProcessing();
        ordering.placeOrder();
        ConfirmationOrder confirmationOrder = new ConfirmationOrder();
        confirmationOrder.thankYouTextLocator.shouldHave(text("Спасибо! Ваш заказ был получен."));
        mainMenu.goToSection("Главная");
        mainMenu.goToFirstProduct();
        new CardProduct().addToCart();
        headerBlock.goToCart();
        cart.applyCoupon(coupon);
        cart.totalPriceLocator.shouldHave(text(String.valueOf(priceBeforeDiscount).replace(".", ",")));
        cart.couponWasAppliedMessage.shouldBe(visible);
    }
}
