package com.automationexercises.tests.ui;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.ProductDetails;
import com.automationexercises.pages.ProductPage;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.dataReader.JsonReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class productDetailsTest extends BaseTest {



    @Test
    public void verifyProductDetailsWithoutLoginTC(){

        new ProductPage(driver).navigate()
                .clickOnViewProduct(testData.getJsonData("Product.name"))
                .verifyProductDetails(testData.getJsonData("Product.name"),
                        testData.getJsonData("Product.price"));
    }

    @Test
    public void verifyReviewMessageWithoutLoginTC(){

        new ProductDetails(driver).navigate()
                .addReview(testData.getJsonData("review.name")
                ,testData.getJsonData("review.email")
                ,testData.getJsonData("review.review"))
                .verifyReviewSuccessMsg(testData.getJsonData("messages.reviewMsg"));
    }







    // Configurations

    @BeforeClass
    public void preCondition(){

        testData = new JsonReader("products-details-data");
    }

    @BeforeMethod
    public void setUp(){
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
    }

    @AfterMethod
    public void tearDown(){
        driver.quitDriver();

    }
}
