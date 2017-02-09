package com.github.apycazo.codex.spring.security.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SecuredServiceTest
{
    @Test
    public void publicServiceRequiresNoAuth ()
    {
        when().get("public/service").then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void privateServiceRequiresAuth ()
    {
        when().get("private/service").then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void privateServiceAuth ()
    {
        given()
                // sends auth info without expecting a requirement for it
                .auth().preemptive().basic("gandalf", "the-grey")
                // not the query should go ok
                .when().get("private/service").then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void privateServiceAuthBadRole ()
    {
        given()
                // sends auth info without expecting a requirement for it
                .auth().preemptive().basic("boromir", "of-gondor")
                // not the query should go ok
                .when().get("private/service").then().statusCode(HttpStatus.FORBIDDEN.value());
    }
}