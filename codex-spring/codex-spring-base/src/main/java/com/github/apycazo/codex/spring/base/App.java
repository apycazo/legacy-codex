package com.github.apycazo.codex.spring.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App
{
    public static void main (String [] args)
    {
        new SpringApplication(App.class).run(args);
    }
}
