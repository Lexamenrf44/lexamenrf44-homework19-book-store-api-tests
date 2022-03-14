package com.bookstoreapi.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BookStoreTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://demoqa.com";

    }

}
