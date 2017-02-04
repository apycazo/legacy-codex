package com.github.apycazo.codex.spring.rest.client;

import com.github.apycazo.codex.spring.rest.data.SimpleEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This controller is created to test the RestTemplate and AsyncRestTemplate
 */
@Slf4j
@RestController
@RequestMapping(value = "${codex.spring.rest.slow.path:slow}", produces = MediaType.APPLICATION_JSON_VALUE)
public class SlowRestController
{
    AtomicInteger idGen = new AtomicInteger(1);

    public void resetId ()
    {
        idGen.set(0);
    }

    @PostMapping
    public SimpleEntry activateRequest (@RequestBody SimpleEntry simpleEntry)
    {
        try {
            Thread.sleep(1000);
            simpleEntry.setActive(true);;
        } catch (InterruptedException e) {
            log.warn("error on thread sleep");
        }

        return simpleEntry;
    }
}
