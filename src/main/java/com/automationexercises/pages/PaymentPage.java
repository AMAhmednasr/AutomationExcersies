package com.automationexercises.pages;

import com.automationexercises.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {
    private GUIDriver driver;
    public PaymentPage(GUIDriver driver) {
        this.driver = driver;
    }

    // Variables

    private String paymentEndpoint = "/payment";
    //Locators

    private final By nameOnCard = By.name("name_on_card");
    private final By cardNumber = By.name("card_number");
    private final By cvc = By.name("cvc");
    private final By expiryMonth = By.name("expiry_month");
    private final By expiryYear = By.name("expiry_year");
    private final By payButton = By.id("submit");
    private final By paymentSuccessMsg = By.xpath("//div[@class=\"col-sm-9 col-sm-offset-1\"]/p");
    private final By downloadInvoiceButton = By.xpath("//a[@class=\"btn btn-default check_out\"]");

    // Actions

    @Step("Fill Card Info")
    public PaymentPage fillCardInfo(String name, String cardNumber,
                                    String cvc, String expiryMonth,
                                    String expiryYear)
    {
        driver.element().type(nameOnCard,name)
                .type(this.cardNumber,cardNumber)
                .type(this.cvc,cvc)
                .type(this.expiryMonth,expiryMonth)
                .type(this.expiryYear,expiryYear)
                .click(payButton);

        return this;
    }

    @Step("Click on downlod invoice button")
    public PaymentPage clickOmDownloadInvoiceButton()
    {
        driver.element().click(downloadInvoiceButton);
        return this;
    }

    // Validations

    @Step("Validate Payment success message")
    public PaymentPage VerifyPaymentSuccessMessage(String excpectedMsg){
        driver.verification().Equals(driver.element().getText(paymentSuccessMsg) , excpectedMsg , "Success Message does't matched");
    return this;

    }

    public PaymentPage verifyDownloadedFile(String InvoiceName) {
        driver.verification().assertFileExists(InvoiceName, "File is not existed");
        return this;

    }
}
