package com.automationexercises.pages;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    private final GUIDriver driver;

    public CheckoutPage(GUIDriver driver) {
        this.driver = driver;

    }

    private String CheckoutEndPoint = "/checkout";

    // Variables


    // Locators

    private final By placeOrderButton = By.xpath(" //a[.='Place Order']");
    // Delivery Address Locators
    private final By deliveryName = By.xpath("//ul[@class=\"address item box\"]/li[@class=\"address_firstname address_lastname\"]");
    private final By deliveryCompany = By.xpath("//ul[@class=\"address item box\"]/li[@class=\"address_address1 address_address2\"][1]");
    private final By deliveryAddress1 = By.xpath("//ul[@class=\"address item box\"]/li[@class=\"address_address1 address_address2\"][2]");
    private final By deliveryAddress2 = By.xpath("//ul[@class=\"address item box\"]/li[@class=\"address_address1 address_address2\"][3]");
    private final By deliveryCityStateZip = By.xpath("//ul[@class=\"address item box\"]/li[@class=\"address_city address_state_name address_postcode\"]");
    private final By deliveryCountry = By.xpath("//ul[@class=\"address item box\"]/li[@class=\"address_country_name\"]");
    private final By deliveryPhone = By.xpath("//ul[@class=\"address alternate_item box\"]/li[@class=\"address_phone\"]");

    // Billing Address Locators
    private final By billingName = By.xpath("//ul[@class=\"address alternate_item box\"]/li[@class=\"address_firstname address_lastname\"]");
    private final By billingCompany = By.xpath("//ul[@class=\"address alternate_item box\"]/li[@class=\"address_address1 address_address2\"][1]");
    private final By billingAddress1 = By.xpath("//ul[@class=\"address alternate_item box\"]/li[@class=\"address_address1 address_address2\"][2]");
    private final By billingAddress2 = By.xpath("//ul[@class=\"address alternate_item box\"]/li[@class=\"address_address1 address_address2\"][3]");
    private final By billingCityStateZip = By.xpath("//ul[@class=\"address alternate_item box\"]/li[@class=\"address_city address_state_name address_postcode\"]");
    private final By billingCountry = By.xpath("//ul[@class=\"address alternate_item box\"]/li[@class=\"address_country_name\"]");
    private final By billingPhone = By.xpath("//ul[@class=\"address alternate_item box\"]/li[@class=\"address_phone\"]");

    // Actions
    @Step("Navigate to CheckOut Page")
    public CheckoutPage navigate() {

        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + CheckoutEndPoint);
        return this;
    }

    @Step("Click on Palce Order Button")
    public PaymentPage clickOnPlaceOrder() {
        driver.element().click(placeOrderButton);
        return new PaymentPage(driver);
    }

    // Valifations

    @Step("Verify Delivery Address")
    public CheckoutPage verifyDeliveryAddress(String title, String fName, String lName, String company, String address1, String address2,
                                              String city, String state, String zip, String country, String phone) {

        driver.validation().Equals(driver.element().getText(deliveryName), (title + ". " + fName + " " + lName), "Delivery name not matched");

        driver.validation().Equals(driver.element().getText(deliveryCompany), company, "Delivery company not matched");

        driver.validation().Equals(driver.element().getText(deliveryAddress1), address1, "Delivery address 1  not matched");

        driver.validation().Equals(driver.element().getText(deliveryAddress2), address2, "Delivery address 2 not matched");

        driver.validation().Equals(driver.element().getText(deliveryCityStateZip), (city + " " + state + " " + zip), "Delivery city, state and ZIP not matched");

        driver.validation().Equals(driver.element().getText(deliveryCountry), country, "Delivery country not matched");

        driver.validation().Equals(driver.element().getText(deliveryPhone), phone, "Delivery phone not matched");

        return this;
    }

    @Step("Verify Billing Address")
    public CheckoutPage verifyBillingAddress(String title, String fName, String lName, String company, String address1, String address2,
                                              String city, String state, String zip, String country, String phone) {

        driver.validation().Equals(driver.element().getText(billingName), (title + ". " + fName + " " + lName), "Billing name not matched");

        driver.validation().Equals(driver.element().getText(billingCompany), company, "Billing company not matched");

        driver.validation().Equals(driver.element().getText(billingAddress1), address1, "Billing address 1  not matched");

        driver.validation().Equals(driver.element().getText(billingAddress2), address2, "Billing address 2 not matched");

        driver.validation().Equals(driver.element().getText(billingCityStateZip), (city + " " + state + " " + zip), "Billing city, state and ZIP not matched");

        driver.validation().Equals(driver.element().getText(billingCountry), country, "Billing country not matched");

        driver.validation().Equals(driver.element().getText(billingPhone), phone, "Billing phone not matched");

        return this;
    }

}
