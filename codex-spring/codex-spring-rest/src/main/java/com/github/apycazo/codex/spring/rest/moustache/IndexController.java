package com.github.apycazo.codex.spring.rest.moustache;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.util.Map;

@Controller
public class IndexController
{
    @Data
    private class Version
    {
        private int number;
    }

    @GetMapping("/moustache")
    public String getIndex (Map<String, Object> model)
    {
        model.put("controller", getClass().getSimpleName());
        model.put("epoch", Instant.now().getEpochSecond());
        Version version = new Version();
        version.setNumber(1);
        model.put("version", version);
        return "index";
    }
}
