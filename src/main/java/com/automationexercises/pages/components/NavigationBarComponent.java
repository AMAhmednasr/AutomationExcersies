package com.automationexercises.pages.components;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.*;
import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {

    private final GUIDriver driver;

    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    // Locators for navigation bar elements

    private final By homeButton= By.xpath("//a[.=' Home']");
    private final By productsButton= By.cssSelector("a[href='/products']");
    private final By cartButton= By.xpath("//a[.=' Cart']");
    private final By signupLoginButton= By.xpath("//a[.=' Signup / Login']");
    private final By logoutButton= By.xpath("//a[.=' Logout']");
    private final By testCasesButton= By.xpath("//a[.=' Test Cases']");
    private final By deleteAccount= By.xpath("//a[.='  Delete Account']");
    private final By apiTestingButton= By.xpath("//a[.=' API Testing']");
    private final By contactUsButton= By.xpath("//a[.=' Contact us']");
    private final By VideoTutorialsButton= By.xpath("//a[.=' Video Tutorials']");
    private final By homePagelabel= By.cssSelector("h1 > span");
    private final By userLabel = By.tagName("b");


    // Actions

    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on Home Button")
    public NavigationBarComponent clickOnHomeButton(){
        driver.element().click(homeButton);
        return this;
    }

    @Step("Click on Product Button")
    public ProductPage clickOnProductButton(){
        driver.element().click(productsButton);
        return new ProductPage(driver);
    }


    @Step("Click on Cart Button")
    public CartPage clickCartButton(){
        driver.element().click(cartButton);
        return new CartPage(driver);
    }

    @Step("Click on Logout Button")
    public LogoutPage clickOnLogoutButton(){
        driver.element().click(logoutButton);
        return new LogoutPage(driver);
    }

    @Step("Click on signup / Login Button")
    public SignupLoginPage clickOnSignupLoginButton(){
        driver.element().click(signupLoginButton);
        return new SignupLoginPage(driver);
    }

    @Step("Click on Test Cases Button")
    public TestCasesPage  clickOnTestCasesButton(){
        driver.element().click(testCasesButton);
        return new TestCasesPage(driver);
    }

    @Step("Click on Delete Account Button")
    public DeleteAccountPage  clickOnDeleteAccountButton(){
        driver.element().click(deleteAccount);
        return new DeleteAccountPage(driver);
    }

    @Step("Click on contact Us Button")
    public ContactUsPage  clickOnContactUsButton(){
        driver.element().click(deleteAccount);
        return new ContactUsPage(driver);
    }

    // Validations

    @Step("Verify Home Page Label")
    public NavigationBarComponent verifyHomePage(){
        driver.verification().isElementVisible(homePagelabel);
        return this;
    }

    @Step("Verify User Label")
    public NavigationBarComponent verifyUserLabel(String expectedName){
        String actualName = driver.element().getText(userLabel);
        LogsManager.info("Verifying user label " + actualName);
        driver.verification().Equals(actualName, expectedName , "User label does not match Expected: " + expectedName + ", Actual: " + actualName );
        return this;
    }
}
