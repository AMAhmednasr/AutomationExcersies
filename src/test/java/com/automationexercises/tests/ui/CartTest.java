package com.automationexercises.tests.ui;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.CartPage;
import com.automationexercises.pages.ProductPage;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Automation Exercises")
@Feature("UI Cart Management")
@Story("products Management")
@Owner("Ahmed Abdelmoneim")
@Severity(SeverityLevel.CRITICAL)
public class CartTest extends BaseTest {



    @Test
    public void verifyProductdetailsOnCart(){
        new ProductPage(driver).navigate()
                .clickOnAddToCart(testData.getJsonData("products.name"))
                .validateItemAddedLabel(testData.getJsonData("messages.cartAdded"))
                .clickOnViewCart()
                .verifyProductDetailsOnCart(testData.getJsonData("products.name")
                ,testData.getJsonData("products.price")
                ,testData.getJsonData("products.quantity")
                ,testData.getJsonData("products.total"));

    }



    @BeforeClass
    public void preCondition(){

        testData = new JsonReader("cart-data");
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
