package com.github.apycazo.codex.spring.rest.client;

import com.github.apycazo.codex.spring.rest.data.SimpleEntry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Collections;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@Slf4j
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "codex.spring.rest.slow.path:" + SlowRestControllerTest.PATH })
@RunWith(SpringRunner.class)
public class SlowRestControllerTest
{

    public static final String PATH = "slow";

    public AsyncRestTemplate async = new AsyncRestTemplate();

    @LocalServerPort
    private Integer port;

    @Test
    public void baseRequest() throws Exception
    {
        // Create headers
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);

        // create entity to be sent
        SimpleEntry entry = SimpleEntry.builder()
                .id(1)
                .serial(UUID.randomUUID().toString())
                .active(false)
                .name("test").build();
        assertFalse(entry.getActive());
        HttpEntity<SimpleEntry> entity = new HttpEntity<>(entry, jsonHeaders);

        // Set target
        String target = StringUtils.applyRelativePath("http://127.0.0.1:"+port+"/", PATH);

        // send async post request
        ListenableFuture<ResponseEntity<SimpleEntry>> futureEntity;
        futureEntity = async.exchange(target, HttpMethod.POST, entity, SimpleEntry.class);

        // add callbacks (success: do nothing, failure: log error)
        futureEntity.addCallback(
                (ok) -> log.info("Got response"),
                (ko) -> log.warn("Forwarding error: {} ({})", ko.getClass().getSimpleName(), ko.getMessage())
        );

        // This actually makes the call synchronous, but it is needed for the test to be simple
        SimpleEntry responseValue = futureEntity.get().getBody();
        assertTrue(responseValue.getActive());

    }

}