package com.automationexercises.tests.ui;

import com.automationexercises.apis.UserManagmentAPI;
import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.*;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.dataReader.JsonReader;
import com.automationexercises.utils.logs.LogsManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Epic("Automation Exercises")
@Feature("UI Invoice")
@Story("Invoice")
@Owner("Ahmed Abdelmoneim")
@Severity(SeverityLevel.CRITICAL)

public class InvoiceTest  extends BaseTest {

    private String email;

    @Test
    public void registerNewAccount() {

        new UserManagmentAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        email,
                        testData.getJsonData("password"),
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyUserCreatedSuccessfully();
        LogsManager.info(email);
    }

    @Test(dependsOnMethods = "registerNewAccount")
    public void loginToAccount() {
        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(email)
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton().navigationBarComponent
                .verifyUserLabel(testData.getJsonData("name"));

    }

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount"})
    public void addProductToCart() {

        new ProductPage(driver).navigate()
                .clickOnAddToCart(testData.getJsonData("products.name"))
                .validateItemAddedLabel(testData.getJsonData("messages.cartAdded"))
                .clickOnViewCart()
                .verifyProductDetailsOnCart(testData.getJsonData("products.name")
                        , testData.getJsonData("products.price")
                        , testData.getJsonData("products.quantity")
                        , testData.getJsonData("products.total"));

    }

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount", "addProductToCart"})
    public void checkout() {
        new CartPage(driver).clickOnProceedToCheckout()
                .verifyBillingAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyBillingAddress(testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber"));
    }


    @Test(dependsOnMethods = {"checkout","loginToAccount", "registerNewAccount", "addProductToCart"})
    public void paymentTest(){
        new CheckoutPage(driver)
                .clickOnPlaceOrder()
                .fillCardInfo(testData.getJsonData("payment.name"),
                        testData.getJsonData("payment.cardNumber"),
                        testData.getJsonData("payment.cvc"),
                        testData.getJsonData("payment.expiryMonth"),
                        testData.getJsonData("payment.expiryYear"))
                .VerifyPaymentSuccessMessage(testData.getJsonData("messages.paymentSuccessMsg"));

    }


    @Test(dependsOnMethods = { "paymentTest" ,"checkout","loginToAccount", "registerNewAccount", "addProductToCart"})
    public void downloadInvoice(){
        new PaymentPage(driver).clickOmDownloadInvoiceButton()
                .verifyDownloadedFile(testData.getJsonData("invoiceName"));
    }

    @Test(dependsOnMethods = {"paymentTest" , "checkout" , "loginToAccount" , "registerNewAccount"} )
    public void deleteAccountAsPostCondition(){
        new UserManagmentAPI().deleteUserAccount(email,
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfuly();
    }

    // Configurations

    @BeforeClass
    public void setUp() {

        testData = new JsonReader("checkoutData");
        email = testData.getJsonData("email")
                + TimeManager.getSimpleTimestamp() + "@gmail.com";
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
    }


    @AfterClass
    public void tearDown() {
        driver.quitDriver();

    }
}
