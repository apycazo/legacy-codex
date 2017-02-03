package com.github.apycazo.codex.spring.rest.feign;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignedService implements FeignedServiceAPI
{
    @Override
    public String echo(@PathVariable String text)
    {
        return text;
    }

    @Override
    public String reverse(@RequestBody String text)
    {
        return new StringBuilder(text).reverse().toString();
    }
}
