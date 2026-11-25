package com.automationexercises.apis;

import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import com.automationexercises.validation.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagmentAPI {

        RequestSpecification requestSpecification;
        Response response;
        Verification verification;

    public UserManagmentAPI(){

        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    // endpoints
    private static final String createAccount_endpoint = "/createAccount";
    private static final String deleteAccount_endpoint = "/deleteAccount";

    // API methods
@Step("Create a new user account with full details")
    public UserManagmentAPI createRegisterUserAccount(String name,
                                                      String email,
                                                      String password,
                                                      String title,
                                                      String birth_date,
                                                      String birth_month,
                                                      String birth_year,
                                                      String firstname,
                                                      String lastname,
                                                      String company,
                                                      String address1,
                                                      String address2,
                                                      String country,
                                                      String state,
                                                      String city,
                                                      String zipcode,
                                                      String mobile_number){

    Map<String, String> formParams = new HashMap<>();

    formParams.put("name", name);
    formParams.put("email", email);
    formParams.put("password", password);
    formParams.put("title", title);
    formParams.put("birth_date", birth_date);
    formParams.put("birth_month", birth_month);
    formParams.put("birth_year", birth_year);
    formParams.put("firstname", firstname);
    formParams.put("lastname", lastname);
    formParams.put("company", company);
    formParams.put("address1", address1);
    formParams.put("address2", address2);
    formParams.put("country", country);
    formParams.put("state", state);
    formParams.put("city", city);
    formParams.put("zipcode", zipcode);
    formParams.put("mobile_number", mobile_number);

    response = requestSpecification.spec(Builder.getUserManagementSpecification(formParams))
            .post(createAccount_endpoint);

    LogsManager.info(response.asPrettyString());
    return this;
}


    @Step("Create a new user account with Minimal details")
    public UserManagmentAPI createRegisterUserAccount(String name,
                                                      String email,
                                                      String password,
                                                      String firstname,
                                                      String lastname
                                                     ){

        Map<String, String> formParams = new HashMap<>();

        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", "Mr");
        formParams.put("birth_date", "1");
        formParams.put("birth_month",  "January");
        formParams.put("birth_year", "1990");
        formParams.put("firstname", firstname);
        formParams.put("lastname", lastname);
        formParams.put("company", "company");
        formParams.put("address1", "address1");
        formParams.put("address2", "address2");
        formParams.put("country", "India");
        formParams.put("state", "state");
        formParams.put("city", "city");
        formParams.put("zipcode", "12345");
        formParams.put("mobile_number", "01129393824");

        response = requestSpecification.spec(Builder.getUserManagementSpecification(formParams))
                .post(createAccount_endpoint);

        LogsManager.info(response.asPrettyString());
        return this;
    }

@Step("Delete user account by email")
public UserManagmentAPI deleteUserAccount(String email , String password){
    Map<String, String> formParams = new HashMap<>();
    formParams.put("email" , email);
    formParams.put("password" , password);
    response = requestSpecification.spec(Builder.getUserManagementSpecification(formParams))
            .delete(deleteAccount_endpoint);

    LogsManager.info(response.asPrettyString());

    return this;
}


    // Validations

    @Step("Verify that user is created successfully")
    public UserManagmentAPI verifyUserCreatedSuccessfully(){
        verification.Equals(response.jsonPath().get("message") , "User created!", "User is not created successfully");
        return this;
    }


    @Step("Verify that user is deleted successfuly")
    public UserManagmentAPI verifyUserDeletedSuccessfuly(){
        verification.Equals(response.jsonPath().get("message"), "Account deleted!" , "User is not deleted successfuly");
        return this;
    }
}
