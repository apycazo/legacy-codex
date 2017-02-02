package com.github.apycazo.codex.spring.rest.cors;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * Note that testing this controller requires an external client (like a javascript request).
 */
@Slf4j
@RestController
// CORS can be enabled at controller level, to apply to all methods
@CrossOrigin(value = "http://10.100.100.10")
public class CorsController
{
    // Apply specific CORS on this method
    @CrossOrigin(value = "*")
    @GetMapping(value = "cors/any")
    public Pair<String,Long> getEntryFromAnywhere ()
    {
        return Pair.of("epoch-any", Instant.now().getEpochSecond());
    }

    @CrossOrigin(value = "http://127.127.127.127")
    @GetMapping(value = "cors/no")
    public Pair<String,Long> getEntryFromNowhere ()
    {
        return Pair.of("epoch-no", Instant.now().getEpochSecond());
    }

    // The CORS settings for this entry point is defined on CorsConfig.class.
    @GetMapping(value = "cors/configured")
    public Pair<String,Long> getEntryFromConfigured ()
    {
        return Pair.of("epoch-configured", Instant.now().getEpochSecond());
    }

}
