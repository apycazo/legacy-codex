package com.github.apycazo.codex.spring.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This could have used @WebMvcTest(UserController.class), but this gives another option.
 */
@Slf4j
@ActiveProfiles("user-test") // not really needed, just an example
@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {UserController.class},
        properties = {"codex.spring.rest.user.path:" + UserControllerTest.UserServicePath}
)
public class UserControllerTest
{
    static final String UserServicePath = "/user";

    @LocalServerPort
    private Integer port;

    @Autowired
    private UserController userController;

    private JacksonTester<UserInfo> json;

    public UserControllerTest ()
    {
        // Init mapper for the Json tester
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Before
    public void setRestAssuredPort()
    {
        RestAssured.port = port;
    }

    /**
     * Just to test the query, directory should be empty anyway
     */
    @Test
    public void deleteAllRecords()
    {
        // send a delete query, expecting a 200 OK
        when().delete(UserServicePath).then().statusCode(HttpStatus.OK.value());
        // check that store is empty
        assertEquals("Directory should be empty!", 0, userController.getUserDirectory().size());
    }

    @Test
    public void createRecord()
    {
        UserInfo info = UserInfo.builder().id(0).username("Gandalf the grey").build();
        UserInfo persistedUser = given()
                .contentType(ContentType.JSON)
                .body(info)
                .when().post(UserServicePath)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .assertThat()
                .body("id", not(0))
                .body("username", equalTo(info.getUsername()))
                .and().extract().body().as(UserInfo.class);

        log.info("Created user info record: {}", persistedUser.toString());
    }

    @Test
    public void updateRecordWithoutRoles()
    {
        UserInfo info = UserInfo.builder().id(0).username("Gandalf the grey").roles(Collections.singletonList("wizard")).build();
        info = given()
                .contentType(ContentType.JSON)
                .body(info)
                .when()
                .post(UserServicePath)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .assertThat()
                .body("roles", hasItem("wizard"))
                .and().extract().body().as(UserInfo.class);

        // keep the ID to make sure it is updated, and not created
        int originalId = info.getId();

        // Create a new one, and try to update
        info.setUsername("Gandalf the white");
        // Remove role, this should have no effect on final value
        info.setRoles(null);
        // Commit the update, and check that roles still contain 'wizard' and name has been changed.
        info = given()
                .contentType(ContentType.JSON)
                .body(info)
                .when()
                .put(UserServicePath)
                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("roles", hasItem("wizard"))
                .body("username", equalTo("Gandalf the white"))
                .and().extract().body().as(UserInfo.class);

        assertEquals("User ID has changed on update", originalId, info.getId());
        log.info("Final updated user: {}", info.toString());
    }

    @Test
    public void checkJsonViewResponses() throws IOException
    {
        // Remove everything to start
        deleteAllRecords();
        String name = "Gandalf the grey";
        // Create a new record
        UserInfo info = UserInfo.builder()
                .id(0)
                .username(name)
                .roles(Collections.singletonList("wizard"))
                .internalId(Long.MAX_VALUE)
                .build();
        info = given()
                .contentType(ContentType.JSON)
                .body(info)
                .when().post(UserServicePath)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .assertThat()
                .body("id", not(0))
                .body("username", equalTo(info.getUsername()))
                .and().extract().body().as(UserInfo.class);

        int userId = info.getId();

        info = when()
                .get(UserServicePath + "/" + userId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().extract().body().as(UserInfo.class);

        // when fetched using ID, should contain all fields
        JsonContent<UserInfo> jsonContent = json.write(info);
        jsonContent.assertThat()
                .hasJsonPathNumberValue("id")
                .hasJsonPathStringValue("username")
                .hasJsonPathArrayValue("roles")
                .hasJsonPathNumberValue("createdOn")
                .hasJsonPathNumberValue("updatedOn")
                .hasJsonPathStringValue("internalId");

        jsonContent.assertThat().extractingJsonPathStringValue("username").isEqualTo(name);

        // Now extract with findAll
        UserInfo [] entries = when()
                .get(UserServicePath)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and().extract().body().as(UserInfo[].class);

        assertTrue("Find all users returned empty list", entries.length > 0);

        // Only id and username should have been returned now
        jsonContent = json.write(entries[0]);
        jsonContent.assertThat()
                .hasJsonPathNumberValue("id")
                .hasJsonPathStringValue("username")
                .doesNotHaveJsonPathValue("roles")
                .doesNotHaveJsonPathValue("createdOn")
                .doesNotHaveJsonPathValue("updatedOn")
                .doesNotHaveJsonPathValue("internalId");
    }
}
