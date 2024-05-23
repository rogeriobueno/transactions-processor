package com.planet.transactionapp.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

    }

    private RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBasePath("/api/v1/transactions")
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    public void shouldRetrieveTransactionById() {
        given(getRequestSpecification())
                .contentType(ContentType.JSON)
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("amount", equalTo(558.35F))
                .body("currency", equalTo("GBP"))
                .body("creditCardName", equalTo("David"))
                .body("recipientName", equalTo("Charlie"));
    }


    @Test
    public void shouldRetrieveStatusCode404InTransactionById() {
        given(getRequestSpecification())
                .contentType(ContentType.JSON)
                .when()
                .get("/amount/recipient/Luis")
                .then()
                .statusCode(200)
                .body("content.size()", equalTo(0));
    }

    @Test
    public void shouldCalculateTotalAmountByRecipient() {
        given(getRequestSpecification())
                .contentType(ContentType.JSON)
                .when()
                .get("amount/recipient/Charlie")
                .then()
                .statusCode(200)
                .body("countryName", notNullValue())
                .body("totalSum", notNullValue());
    }

    @Test
    public void shouldCalculateTotalAmountByCountry() {
        given(getRequestSpecification())
                .contentType(ContentType.JSON)
                .when()
                .get("amount/country")
                .then()
                .statusCode(200)
                .body("content.size()", equalTo(10));
    }

    @Test
    public void shouldRetrieveAllTransactionsByCreditCardNumber() {
        given(getRequestSpecification())
                .contentType(ContentType.JSON)
                .when()
                .get("credit-card/123456781234207")
                .then()
                .statusCode(200)
                .body("content.size()", equalTo(3))
                .body("content[0].creditCardNumber", equalTo("123456781234207"))
                .body("content[1].creditCardNumber", equalTo("123456781234207"))
                .body("content[2].creditCardNumber", equalTo("123456781234207"));
    }

}