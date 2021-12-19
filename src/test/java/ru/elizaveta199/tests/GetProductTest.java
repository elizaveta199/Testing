package ru.elizaveta199.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.when;

public class GetProductTest {
    Properties properties = new Properties();

    public static final String CATEGORY_ENDPOINTS = "products/{id}";

    @BeforeEach
    void setup() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    /**
     * В тесте указать id существующего продукта
     */
    @Test
    void getProductPositiveTest() {
        when()
                .get(CATEGORY_ENDPOINTS, 18723)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    /**
     * В тесте указать id удаленного продукта
     */
    @Test
    void getProductTestDelete() {
        when()
                .get(CATEGORY_ENDPOINTS, 19231)
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    /**
     * В тесте указать id продуктв не использованный
     */
    @Test
    void getProductTestNonExistent() {
        when()
                .get(CATEGORY_ENDPOINTS, 999999999)
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void getProductTestZero() {
        when()
                .get(CATEGORY_ENDPOINTS, 0)
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void getProductNegativeTest() {
        when()
                .get(CATEGORY_ENDPOINTS, -6)
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void getProductTestWithoutId() {
        when()
                .get("/products")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
