package com.automationexercises.pages;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {

    private final String signUpLoginEndpoint= "/login";
    private GUIDriver driver;
  public   NavigationBarComponent navigationBarComponent;
    public SignupLoginPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }

    // Locators
        private final By loginEmail = By.cssSelector("[data-qa=\"login-email\"]");
        private final By loginPassword = By.cssSelector("[data-qa=\"login-password\"]");
        private final By loginButton = By.cssSelector("[data-qa=\"login-button\"]");
        private final By signUpName = By.cssSelector("[data-qa=\"signup-name\"]");
        private final By signUpEmail = By.cssSelector("[data-qa=\"signup-email\"]");
        private final By signUpButton = By.cssSelector("[data-qa=\"signup-button\"]");
        private final By signUpLabel = By.cssSelector(".signup-form > h2");
        private final By loginError = By.cssSelector(".login-form p");
        private final By registerError = By.cssSelector(".signup-form p");


    // Actions

    @Step("Navigate to SignUp / Login page")
    public SignupLoginPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + signUpLoginEndpoint);
        return this;
    }

@Step("Enter Login Email")
    public SignupLoginPage enterLoginEmail(String email){
    driver.element().type(loginEmail , email);
    return this;
}

    @Step("Enter Login Password")
    public SignupLoginPage enterLoginPassword(String password){
        driver.element().type(loginPassword , password);
        return this;
    }

    @Step("Click on Login Button")
    public SignupLoginPage clickOnLoginButton(){
        driver.element().click(loginButton);
        return this;
    }

    // Signup Actions

    @Step("Enter Signup Name: {name}")
    public SignupLoginPage enterSignupName(String name) {
        driver.element().type(signUpName, name);
        return this;
    }

    @Step("Enter Signup Email: {email}")
    public SignupLoginPage enterSignupEmail(String email) {
        driver.element().type(signUpEmail, email);
        return this;
    }

    @Step("Click on Signup Button")
    public SignupLoginPage clickOnSignupButton() {
        driver.element().click(signUpButton);
        return new SignupLoginPage(driver);
    }

    //Validations


    @Step("Verify New User Signup! is visible ")
    public SignupLoginPage verifyNewUserSignUpVisible(){
            driver.verification().isElementVisible(signUpLabel);
            return this;
    }

    @Step("Verify Login Error Message: {errorExpected}")
    public SignupLoginPage verifyLoginErrorMsg(String errorExpected) {
            String errorActual = driver.element().getText(loginError);
            driver.verification().Equals(errorActual , errorExpected , "Login Error Message isn't as expected");
        return this;
    }

    @Step("Verify Register Error Message: {errorExpected}")
    public SignupLoginPage verifyRegisterErrorMsg(String errorExpected) {
        String errorActual = driver.element().getText(registerError);
        driver.verification().Equals(errorActual, errorExpected, "Register Error Message isn't as expected");
        return this;
    }



}
