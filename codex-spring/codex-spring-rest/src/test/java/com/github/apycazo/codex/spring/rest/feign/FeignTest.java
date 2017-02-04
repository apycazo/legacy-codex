package com.github.apycazo.codex.spring.rest.feign;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port:19999")
public class FeignTest
{
    @LocalServerPort
    private Integer port;

    @Autowired
    private FeignedServiceClient client;

    @Test
    public void testGetRequest ()
    {
        assertNotNull("Feigned client not instanced", client);
        String initialValue = "feigned";
        String reversedValue = "dengief";

        String response = client.echo(initialValue);
        assertEquals("Echo value response not valid", initialValue, response);
    }

    @Test
    public void testPostRequest ()
    {
        assertNotNull("Feigned client not instanced", client);
        String initialValue = "feigned";
        String reversedValue = "dengief";

        String response = client.reverse(initialValue);
        assertEquals("Reversed value response not valid", reversedValue, response);
    }

}