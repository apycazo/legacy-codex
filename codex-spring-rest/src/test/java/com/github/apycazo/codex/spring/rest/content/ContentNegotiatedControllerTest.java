package com.github.apycazo.codex.spring.rest.content;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;

@Slf4j
@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ContentNegotiatedController.class},
        properties = {ContentNegotiatedController.PATH_PROP + ":" + ContentNegotiatedController.PATH_VALUE_DEFAULT}
)
public class ContentNegotiatedControllerTest
{
    @LocalServerPort
    private Integer port;

    @Before
    public void setRestAssuredPort()
    {
        RestAssured.port = port;
    }

    /**
     * Default configuration is to return JSON content absent 'accept' header and type extension.
     */
    @Test
    public void getDefault()
    {
        when()
                .get(ContentNegotiatedController.PATH_VALUE_DEFAULT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, containsString(MediaType.APPLICATION_JSON_VALUE));
    }

    /**
     * We still process 'accept' headers absent extensions.
     */
    @Test
    public void getWithHeaders()
    {
        given()
                .accept(ContentType.XML)
                .when()
                .get(ContentNegotiatedController.PATH_VALUE_DEFAULT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, containsString(MediaType.APPLICATION_XML_VALUE));
    }

    /**
     * Test requiring JSON through the use of an extension.
     */
    @Test
    public void getWithJsonExtension()
    {
        when()
                .get(ContentNegotiatedController.PATH_VALUE_DEFAULT + ".json")
                .then()
                .statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, containsString(MediaType.APPLICATION_JSON_VALUE));
    }

    /**
     * Test requiring XML through the use of an extension.
     */
    @Test
    public void getWithXmlExtension()
    {
        when()
                .get(ContentNegotiatedController.PATH_VALUE_DEFAULT + ".xml")
                .then()
                .statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, containsString(MediaType.APPLICATION_XML_VALUE));
    }

    /**
     * If we have both an 'accept' header and an extension, we given preference to the extension.
     */
    @Test
    public void getWithJsonExtensionOverridingHeader()
    {
        given()
                // send the 'accept' header
                .accept(ContentType.XML)
                .when()
                // override 'accept' header by using a '.json' extension
                .get(ContentNegotiatedController.PATH_VALUE_DEFAULT + ".json")
                .then()
                .statusCode(HttpStatus.OK.value())
                // result should be json
                .header(HttpHeaders.CONTENT_TYPE, containsString(MediaType.APPLICATION_JSON_VALUE));
    }

}