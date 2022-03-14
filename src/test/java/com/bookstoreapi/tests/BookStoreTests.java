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
    void getUnavailableBookTest() {
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
    void getAvailableBookTest() {
        given()
                .params("ISBN", "9781449325862")//
                .log().uri()
                .log().body()
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("title", is("Git Pocket Guide"))
                .body("description", is("This pocket guide is the perfect on-the-job companion to Git, the distributed version control system. " +
                        "It provides a compact, readable introduction to Git for new users, as well as a reference to common commands and procedures for those of you with Git exp"))
                .body("website", is("http://chimera.labs.oreilly.com/books/1230000000561/index.html"));
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
                .body("books.isbn[1]", is("9781449331818"))
                .body("books.title[2]", is("Designing Evolvable Web APIs with ASP.NET"));
    }

}
