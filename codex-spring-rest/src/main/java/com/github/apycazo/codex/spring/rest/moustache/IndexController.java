package com.github.apycazo.codex.spring.rest.moustache;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.util.Map;

@Controller
public class IndexController
{
    @GetMapping("/moustache")
    public String getIndex (Map<String, Object> model)
    {
        model.put("controller", getClass().getSimpleName());
        model.put("epoch", Instant.now().getEpochSecond());
        return "index";
    }
}
