package com.automationexercises.tests.ui;

import com.automationexercises.apis.UserManagmentAPI;
import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.SignupLoginPage;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Automation Exercises")
@Feature("UI User Management")
@Story("User Login")
@Owner("Ahmed Abdelmoneim")
@Severity(SeverityLevel.CRITICAL)
public class LoginTest extends BaseTest {

    String timeStamp = TimeManager.getSimpleTimestamp();


    @Description("Verify that User can login with valid data")
    @Test
    public void validLoginTC(){


        new UserManagmentAPI().createRegisterUserAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timeStamp + "@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName")
        ).verifyUserCreatedSuccessfully();

       new SignupLoginPage(driver).navigate()
               .enterLoginEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
               .enterLoginPassword(testData.getJsonData("password"))
               .clickOnLoginButton().navigationBarComponent
               .verifyUserLabel(testData.getJsonData("name"));

       new UserManagmentAPI().deleteUserAccount(
               testData.getJsonData("email") + timeStamp + "@gmail.com",
               testData.getJsonData("password")).verifyUserDeletedSuccessfuly();
    }


    @Description("Verify that User can not login In Valid Email")
    @Test
    public void inValidLoginUsingInvalidEmailTC(){


        new UserManagmentAPI().createRegisterUserAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timeStamp+ "@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName")
        ).verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email")  + "a@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton().verifyLoginErrorMsg(testData.getJsonData("messages.error"));

        new UserManagmentAPI().deleteUserAccount(
                testData.getJsonData("email") + timeStamp+ "@gmail.com",
                testData.getJsonData("password")).verifyUserDeletedSuccessfuly();
    }


    @Description("Verify that User can not login In Valid Password")
    @Test
    public void inValidLoginUsingInvalidPasswordTC(){


        new UserManagmentAPI().createRegisterUserAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timeStamp + "@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName")
        ).verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password") + timeStamp)
                .clickOnLoginButton().verifyLoginErrorMsg(testData.getJsonData("messages.error"));

        new UserManagmentAPI().deleteUserAccount(
                testData.getJsonData("email") + timeStamp + "@gmail.com",
                testData.getJsonData("password")).verifyUserDeletedSuccessfuly();
    }

    // Configurations

    @BeforeClass
    public void preCondition(){

        testData = new JsonReader("login-data");
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
