package com.github.apycazo.codex.spring.rest.content;

import com.github.apycazo.codex.spring.rest.data.SimpleEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "${" + ContentNegotiatedController.PATH_PROP + ":" + ContentNegotiatedController.PATH_VALUE_DEFAULT + "}")
public class ContentNegotiatedController
{
    public static final String PATH_PROP = "codex.spring.rest.content.path";
    public static final String PATH_VALUE_DEFAULT = "content-negotiation";

    @GetMapping
    public SimpleEntry get()
    {
        return SimpleEntry.builder()
                .id(1)
                .serial(UUID.randomUUID().toString())
                .active(true)
                .name(PATH_VALUE_DEFAULT)
                .build();
    }
}
