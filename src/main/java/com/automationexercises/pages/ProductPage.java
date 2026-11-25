package com.automationexercises.pages;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class ProductPage {

private final GUIDriver driver;
public NavigationBarComponent navigationBarComponent ;

    public ProductPage(GUIDriver driver) {

        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }

    // Variables

    private final String ProductsEndpoint= "/products";


    // Locators

    private final By searchField = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By itemAddedLabel = By.cssSelector(".modal-body>p");
    private final By continueShoppingButton = By.cssSelector(".modal-footer button");
    private final By viewCartButton = By.cssSelector(".modal-body a[href='/view_cart']");




    // Dynamic Locator

    private By productName(String productName) {
        return By.xpath("//div[@class='overlay-content']//p[normalize-space()='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath("//div[@class='overlay-content']//p[normalize-space()='" + productName + "']/preceding-sibling::h2");
    }

    private By hoverOnProduct(String prductName){
        return By.xpath("//div[@class='productinfo text-center']/p[text()='"+ prductName + "'] " );
    }

    private By addToCartButton(String prductName){
        return By.xpath("//div[@class='overlay-content']//p[text()='"+prductName +"']/following-sibling::a");
    }


    private By viewProduct(String prductName){
        return By.xpath("//p[ .='"+ prductName +"']//following::div[@class=\"choose\"][1]");
    }

    // Actions
    @Step("Navigate to Products Page")
    public ProductPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + ProductsEndpoint);
            return this;
    }


    @Step("Search for product: {productName}")
    public ProductPage searchProduct (String productName){
        driver.element().type(searchField, productName)
                .click(searchButton);
        return this;
    }

    @Step("Clik on Add to Cart for product: {productName} ")
    public ProductPage clickOnAddToCart(String productName){
        driver.element().hover(hoverOnProduct(productName))
                .click(addToCartButton(productName));
        return this;
    }




    @Step("Clik on view product for product {productName} ")
    public ProductDetails clickOnViewProduct(String productName){
        driver.element().click(viewProduct(productName));
        return new ProductDetails(driver);
    }

    @Step("Clik on view cart for product {productName} ")
    public CartPage clickOnViewCart(){
        driver.element().click(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Clik on Continue Shoping ")
    public ProductPage clickOnContinueShoping(){
        driver.element().click(continueShoppingButton);
        return this;
    }

    // Validations

    @Step("Validate product details for {productName} with price {productPrice}")
    public ProductPage validateProductDetails(String productName , String productPrice){

        driver.element().hover(hoverOnProduct(productName));


        String actualProductName = driver.element().getText(productName(productName));
        String actualProductPrice = driver.element().getText(this.productPrice(productName));

        LogsManager.info("Validating product details for: " + actualProductName + " with Price: " + actualProductPrice);

        driver.validation().Equals(actualProductName, productName , "Product name does not match");
        driver.validation().Equals(actualProductPrice, productPrice , "Product price does not match");

        return this;
    }


    @Step("Validate item added label contains: {expectedText}")
    public ProductPage validateItemAddedLabel(String expectedText) {
        String actualTxt = driver.element().getText(itemAddedLabel);

        LogsManager.info("Validating item added Label: " + actualTxt );

        driver.verification().Equals(actualTxt , expectedText , "Item added label {actualTxt} does not match {expectedText}");
return this;
    }
}
