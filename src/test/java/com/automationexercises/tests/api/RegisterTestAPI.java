
package com.automationexercises.tests.api;

import com.automationexercises.apis.UserManagmentAPI;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.dataReader.JsonReader;
import com.automationexercises.utils.logs.LogsManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterTestAPI extends BaseTest {

    private String email;

    @BeforeClass
    public void setUp() {
        testData = new JsonReader("register-data");
        email = testData.getJsonData("email")
                + TimeManager.getSimpleTimestamp() + "@gmail.com";
    }

    @Test
    public void registerTest() {

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
    }
}
