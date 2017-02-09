package com.github.apycazo.codex.spring.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SecuredService
{
    @GetMapping(value = "/private/service")
    public Boolean getResponse ()
    {
        log.info("Secured response");
        return Boolean.FALSE;
    }
}
