package com.github.apycazo.codex.spring.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginError
{
    @RequestMapping("login-error")
    public String returnError ()
    {
        log.warn("login error");
        return "login error";
    }
}
