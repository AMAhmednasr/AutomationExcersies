package com.automationexercises.pages;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.components.NavigationBarComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import javax.swing.*;

public class SignupPage {

    private final GUIDriver driver;
    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }
// Locators

    // Account Information
    private final By name = By.cssSelector("[data-qa='name']");
    private final By email = By.cssSelector("[data-qa='email']");
    private final By password = By.cssSelector("[data-qa='password']");

    // Date of Birth
    private final By day = By.id("days");
    private final By month = By.id("months");
    private final By year = By.id("years");

    // Checkboxes
    private final By newsLetter = By.id("newsletter");
    private final By spacialOffers = By.id("optin");

    // Address Information
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address1 = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");

    // Create Account
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");


    // Account Created
    private final By accountCreatedMessage = By.cssSelector("[data-qa='account-created']");

    // Continue
    private final By continueButton = By.cssSelector("[data-qa='continue-button']");



    // Actions

    @Step("Choose title") // Mr - Mrs
    public SignupPage chooseTitle(String title){
    By titlelocator = By.xpath("//input [@value='" + title + "']");
    driver.element().click(titlelocator);
    return this;
}

@Step("Fill Registration form")
public SignupPage fillRegistrationForm(String title,
                                        String passwordValue,
                                        String dayValue,
                                        String monthValue,
                                        String yearValue,
                                        String firstNameValue,
                                        String lastNameValue,
                                        String companyValue,
                                        String address1Value,
                                        String address2Value,
                                        String countryValue,
                                        String stateValue,
                                        String cityValue,
                                        String zipcodeValue,
                                        String mobileNumberValue)
{
   chooseTitle(title);
    driver.element().type(password, passwordValue);

    driver.element().selectFromDropdown(day, dayValue);
    driver.element().selectFromDropdown(month, monthValue);
    driver.element().selectFromDropdown(year, yearValue);

    driver.element().click(newsLetter);
    driver.element().click(spacialOffers);

    driver.element().type(firstName, firstNameValue);
    driver.element().type(lastName, lastNameValue);
    driver.element().type(company, companyValue);
    driver.element().type(address1, address1Value);
    driver.element().type(address2, address2Value);

    driver.element().selectFromDropdown(country, countryValue);

    driver.element().type(state, stateValue);
    driver.element().type(city, cityValue);
    driver.element().type(zipcode, zipcodeValue);
    driver.element().type(mobileNumber, mobileNumberValue);

    return this;
}

@Step("Click Create Account Button" )
    public SignupPage clickCreateAccountButton(){
        driver.element().click(createAccountButton);
        return this;
}

    // Validation

    @Step("verify Account Created")
public SignupPage verifyAccountCreated(){
        driver.verification().isElementVisible(accountCreatedMessage);
        return this;
    }

    @Step("Click on Continue Button")
    public NavigationBarComponent clickOnContinueButton() {
        driver.element().click(continueButton);
        return new  NavigationBarComponent(driver);
    }
}




