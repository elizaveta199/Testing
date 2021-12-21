package ru.elizaveta199.tests;

import com.github.javafaker.Faker;
import elivaveta199.dto.Product;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostProductTest {
    Product product;
    Product product1;
    Integer id;
    Properties properties = new Properties();
    Faker faker = new Faker();
    public static final String CATEGORY_ENDPOINTS = "products";

    @BeforeEach
    void setup() throws IOException {
        product = Product.builder()
                .price(100)
                .title(faker.food().dish())
                .categoryTitle("Food")
                .build();

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
    void PostProductsPositiveTestCase1() {
        id = given()
                .body(product)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .body("title", CoreMatchers.equalTo(product.getTitle()))
                .body("price", CoreMatchers.equalTo(product.getPrice()))
                .body("categoryTitle", CoreMatchers.equalTo(product.getCategoryTitle()))
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .jsonPath()
                .get("id");
    }

    @Test
    void PostProductsPositiveTestCase2() {
        Product response = given()
                .body(product)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product.getTitle()));
        assertThat(response.getPrice(), equalTo(product.getPrice()));
    }

    @Test
    void PostProductsTestPriceIsNULL() {
        product1 = Product.builder()
                .price(null)
                .title("Bread")
                .categoryTitle("Food")
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestWithoutPrice() {
        product1 = Product.builder()
                .title("Bread")
                .categoryTitle("Food")
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestWithoutPriceAndTitle() {
        product1 = Product.builder()
                .categoryTitle("Food")
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestWithoutAll() {
        product1 = Product.builder()
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestPriceCase2() {
        product1 = Product.builder()
                .price(-900)
                .title("Bread")
                .categoryTitle("Food")
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestPriceCase3() {
        product1 = Product.builder()
                .price(0)
                .title("Bread")
                .categoryTitle("Food")
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestCategoriesCase1() {
        product1 = Product.builder()
                .price(600)
                .title("Peach")
                .categoryTitle("Fruits")
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(500)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestCategoriesCase2() {
        product1 = Product.builder()
                .price(700)
                .title("Peach")
                .categoryTitle("")
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestCategoriesCase3() {
        product1 = Product.builder()
                .price(0)
                .title("Peach")
                .categoryTitle(null)
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestTitleCase1() {
        product1 = Product.builder()
                .price(0)
                .title("")
                .categoryTitle("Food")
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }

    @Test
    void PostProductsTestTitleCase2() {
        product1 = Product.builder()
                .price(0)
                .title(null)
                .categoryTitle("Food")
                .build();
        Product response = given()
                .body(product1)
                .header("Content-Type", "application/json")
                .log()
                .all()
                .expect()
                .statusCode(201)
                .when()
                .post(CATEGORY_ENDPOINTS)
                .prettyPeek()
                .body()
                .as(Product.class);
        id = response.getId();
        assertThat(id, is(not(nullValue())));
        assertThat(response.getCategoryTitle(), equalTo(product1.getCategoryTitle()));
        assertThat(response.getTitle(), equalTo(product1.getTitle()));
        assertThat(response.getPrice(), equalTo(product1.getPrice()));
    }
}
