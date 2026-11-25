package com.automationexercises.tests.ui;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.ProductPage;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.dataReader.JsonReader;
import com.automationexercises.utils.dataReader.PropertyReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercises")
@Feature("UI products Management")
@Story("products Management")
@Owner("Ahmed Abdelmoneim")
@Severity(SeverityLevel.CRITICAL)

public class ProductsTest extends BaseTest {



    @Test
    @Description("Search for products and validate its details")
    public void searchForProductWithoutLogin(){

        new ProductPage(driver).navigate()
                .searchProduct(testData.getJsonData("searchedProduct.name"))
                .validateProductDetails(testData.getJsonData("searchedProduct.name"),
                           testData.getJsonData("searchedProduct.price"));
    }

    @Test
    @Description("Add product to car without login")
    public void addProductWithoutLogin(){

        new ProductPage(driver).navigate()

                .clickOnAddToCart(testData.getJsonData("products1.name"))
                .validateItemAddedLabel(testData.getJsonData("messages.cartAdded"));
    }




    // Configurations

    @BeforeClass
    public void preCondition(){

        testData = new JsonReader("products-data");
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
