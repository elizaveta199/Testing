package ru.elizaveta199.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.elivaveta199.dto.Product;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class PostProductTest {
    Product product;
    Integer id;
    Properties properties = new Properties();

    public static final String CATEGORY_ENDPOINTS = "products";

    @BeforeEach
    void setup() throws IOException {
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
    }

    @AfterEach
    void tearDown() {
        when()
                .delete("products/{id}", id)
                .prettyPeek()
                .then()
                .statusCode(200);
        when()
                .get("products/{id}", id)
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void PostProductsTestPriceCase1() {
        product = Product.builder()
                .price(600)
                .title("Apple")
                .categoryTitle("Food")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestPriceIsNULL() {
        product = Product.builder()
                .price(null)
                .title("Bread")
                .categoryTitle("Food")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestWithoutPrice() {
        product = Product.builder()
                .title("Bread")
                .categoryTitle("Food")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestWithoutPriceAndTitle() {
        product = Product.builder()
                .categoryTitle("Food")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestWithoutAll() {
        product = Product.builder()
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestPriceCase2() {
        product = Product.builder()
                .price(-900)
                .title("Bread")
                .categoryTitle("Food")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestPriceCase3() {
        product = Product.builder()
                .price(0)
                .title("Bread")
                .categoryTitle("Food")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestCategoriesCase1() {
        product = Product.builder()
                .price(600)
                .title("Peach")
                .categoryTitle("Fruits")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestCategoriesCase2() {
        product = Product.builder()
                .price(700)
                .title("Peach")
                .categoryTitle("")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestCategoriesCase3() {
        product = Product.builder()
                .price(0)
                .title("Peach")
                .categoryTitle(null)
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestTitleCase1() {
        product = Product.builder()
                .price(0)
                .title("")
                .categoryTitle("Food")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsTestTitleCase2() {
        product = Product.builder()
                .price(0)
                .title(null)
                .categoryTitle("Food")
                .build();
        id = given()
                .body(product.toString())
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }
}
