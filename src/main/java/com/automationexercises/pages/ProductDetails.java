package com.automationexercises.pages;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.checkerframework.common.value.qual.StringVal;
import org.openqa.selenium.By;

public class ProductDetails {
    private final GUIDriver driver;
    public ProductDetails(GUIDriver driver) {
        this.driver = driver;
    }

    private String PrductDetailsEndPoint = "/product_details/1";

    // Locators

    private final By productName = By.cssSelector(".product-information >h2");
    private final By productPrice = By.cssSelector(".product-information >span >span");

    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By review = By.id("review");
    private final By submitButton = By.id("button-review");
    private final By reviewSuccessMsg = By.cssSelector("#review-section  span");


    // Actions
@Step("Navigate to Product details page")
    public ProductDetails navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + PrductDetailsEndPoint );
        return this;
    }

    @Step("Write review for product")
    public ProductDetails addReview(String name, String email , String review){

        driver.element().type(this.name , name);
        driver.element().type(this.email , email);
        driver.element().type(this.review , review);
        driver.element().click(submitButton);

        return this;
    }


    // Validation

    @Step("verify Product details")
    public ProductDetails verifyProductDetails(String productName , String productPrice){

        String actualProductName = driver.element().getText(this.productName);
        String actualProductprice = driver.element().getText(this.productPrice);
        LogsManager.info("Actual Product name: " , actualProductName ,"And Actual product price: " , actualProductprice);

        driver.validation().Equals(actualProductName, productName, "Product name validation failed");
        driver.validation().Equals(actualProductprice, productPrice, "Product price validation failed");


        return this;

    }

    @Step("Verify review Message")
    public ProductDetails verifyReviewSuccessMsg(String msg ){

        String actualMsg = driver.element().getText(reviewSuccessMsg);
        LogsManager.info("Actual Message" , actualMsg);

        driver.verification().Equals(actualMsg , msg ,"Review message Verfication failed");

        return this;
    }


}
