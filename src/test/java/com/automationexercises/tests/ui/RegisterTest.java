package com.automationexercises.tests.ui;

import com.automationexercises.apis.UserManagmentAPI;
import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.SignupLoginPage;
import com.automationexercises.pages.SignupPage;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.dataReader.JsonReader;
import com.automationexercises.utils.logs.LogsManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Automation Exercises")
@Feature("UI User Management")
@Story("User Registration")
@Owner("Ahmed Abdelmoneim")
@Severity(SeverityLevel.CRITICAL)
public class RegisterTest extends BaseTest {


String timeStamp = TimeManager.getSimpleTimestamp();

    // Tests
    @Description("Verify that User can Sign Up with valid data")
@Test
public void validSignUpTC() {
    new SignupLoginPage(driver).navigate()
            .enterSignupName(testData.getJsonData("name"))
            .enterSignupEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
            .clickOnSignupButton();
            new SignupPage(driver)
            .fillRegistrationForm(
                    testData.getJsonData("titleMale"),
                    testData.getJsonData("password"),
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
            .clickCreateAccountButton()
            .verifyAccountCreated();
}


@Description("Verify that User can Can not Up with In Valid data")
@Test
public void verifyErrorMsgWhenAccountCreatedBefore(){

    // precondition > create a user account with API

    String email = testData.getJsonData("email")
            + TimeManager.getSimpleTimestamp() + "@gmail.com";
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

    new SignupLoginPage(driver).navigate()
            .enterSignupName(testData.getJsonData("name"))
            .enterSignupEmail(email)
            .clickOnSignupButton().verifyRegisterErrorMsg(testData.getJsonData("messages.error"));

    new UserManagmentAPI().deleteUserAccount(
            email,
            testData.getJsonData("password")
            ).verifyUserDeletedSuccessfuly();

}



    // Configurations

    @BeforeClass
    public void preCondition(){

        testData = new JsonReader("register-data");
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
