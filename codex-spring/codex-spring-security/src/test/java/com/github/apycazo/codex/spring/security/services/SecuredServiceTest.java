package com.github.apycazo.codex.spring.security.services;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@Slf4j
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "security.stateless=true" })
@RunWith(SpringRunner.class)
public class SecuredServiceTest
{
    @LocalServerPort
    private Integer port;

    @Before
    public void setRestAssuredPort()
    {
        RestAssured.port = port;
    }

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
                // now the query should go ok
                .when().get("private/service").then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void privateServiceAuthBadRole ()
    {
        given()
                // sends auth info without expecting a requirement for it
                .auth().preemptive().basic("boromir", "of-gondor")
                // now the query should go wrong
                .when().get("private/service").then().statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void checkSessionType ()
    {
        given()
                // sends auth info without expecting a requirement for it
                .auth().preemptive().basic("gandalf", "the-grey")
                // now the query should go ok
                .when().get("private/service").then().statusCode(HttpStatus.OK.value());

        when().get("private/service").then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}