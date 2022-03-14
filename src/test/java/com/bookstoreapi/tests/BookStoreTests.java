package com.bookstoreapi.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static com.bookstoreapi.helpers.CustomAllureListener.withCustomTemplates;

public class BookStoreTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.filters(withCustomTemplates());

    }

    @Test
    void getBookTest() {
        given()
                .params("ISBN", "978144932586288")//
                .log().uri()
                .log().body()
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("message", is("ISBN supplied is not available in Books Collection!"));
    }

    @Test
    void getBooksTest() {
        given()
                .log().all()
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .log().all()
                .body("books", hasSize(greaterThan(0)))
                .body("books.title[0]", is("Git Pocket Guide"));
    }

}
