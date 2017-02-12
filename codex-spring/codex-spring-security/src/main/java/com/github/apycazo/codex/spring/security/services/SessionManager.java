package com.github.apycazo.codex.spring.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class SessionManager
{
    @RequestMapping("/security/logout")
    public void logout (HttpSession session)
    {
        session.invalidate();
        log.info("Session '{}' invalidated (max idle time: '{}')", session.getId(), session.getMaxInactiveInterval());
        // to redirect, return something like: "redirect:/login"
    }
}
