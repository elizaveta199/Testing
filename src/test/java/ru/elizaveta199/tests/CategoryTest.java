package ru.elizaveta199.tests;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.CoreMatchers.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryTest {
    public static final String CATEGORY_ENDPOINTS = "categories/{id}";
static Properties properties=new Properties();
    @BeforeEach
    void setup () throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI=properties.getProperty("baseURL");
        //RestAssured.baseURI = "http://80.78.248.82:8189/market/api/v1";
    }


    @Test
    void CategoryTestCase1() {
        given()
                .when()
                .get(CATEGORY_ENDPOINTS,1)
                .then()
                .statusCode(200);
    }

    @Test
    void CategoryTestWithLogsCase1() {
        given()
                .when()
                .log()
                .uri()
                .log()
                .body()
                .get("/categories/1")
                .prettyPeek()
                .then()
                .statusCode(200);
    }


    @Test
    void CategoryTestWithLogsCase2() {
        //ValidatableResponse validatableResponse =
                given()
                .when()
                .log()
                .uri()
                .log()
                .body()
                .get("/categories/1")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo(1));

        //    assertThat(validatableResponse.body("id", equalTo(1)), is (true));
    }

    @Test
    void CategoryTestWithLogsCase3() {
        Response response = given()
                .when()
                .log()
                .uri()
                .log()
                .body()
                .get("/categories/1")
                .prettyPeek();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().jsonPath().get("products[0].categoryTitle"), equalTo("Food"));
     }
}
