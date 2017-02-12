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

import java.util.Map;

import static io.restassured.RestAssured.given;

@Slf4j
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "security.stateless=false" })
@RunWith(SpringRunner.class)
public class StatefulSecurityTest
{
    @LocalServerPort
    private Integer port;

    @Before
    public void setRestAssuredPort()
    {
        RestAssured.port = port;
    }

    @Test
    public void checkSessionType ()
    {
        String sessionCookieID = "JSESSIONID";
        // sends the request and extracts cookies
        // the cookie we need for auth is something like: {'JSESSIONID' : 'D4959F03E35AE230907864BD346D00A3'}
        Map<String,String> cookies = given()
                // sends auth info without expecting a requirement for it
                .auth().preemptive().basic("gandalf", "the-grey")
                // not the query should go ok
                .when()
                .get("private/service")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .cookies();
        // sends request without auth info, but including cookies
        given()
                .auth().none()
                .cookie(sessionCookieID, cookies.get(sessionCookieID))
                .when().get("private/service").then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void checkLogout ()
    {
        String sessionCookieID = "JSESSIONID";
        // sends the request and extracts cookies
        // the cookie we need for auth is something like: {'JSESSIONID' : 'D4959F03E35AE230907864BD346D00A3'}
        Map<String,String> cookies = given()
                // sends auth info without expecting a requirement for it
                .auth().preemptive().basic("gandalf", "the-grey")
                // not the query should go ok
                .when()
                .get("private/service")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .cookies();
        // sends request without auth info, but including cookies
        given()
                .auth().none()
                .cookie(sessionCookieID, cookies.get(sessionCookieID))
                .when().get("private/service").then().statusCode(HttpStatus.OK.value());
        // log out
        given()
                // session can be provided using auth, but we will use the cookie
                // .auth().preemptive().basic("gandalf", "the-grey")
                .cookie(sessionCookieID, cookies.get(sessionCookieID))
                .when().post("security/logout").then().statusCode(HttpStatus.OK.value());
        // cookie is not valid anymore
        given()
                .auth().none()
                .cookie(sessionCookieID, cookies.get(sessionCookieID))
                .when().get("private/service").then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
