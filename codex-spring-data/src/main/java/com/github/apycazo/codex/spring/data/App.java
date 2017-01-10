package com.github.apycazo.codex.spring.data;

import com.github.apycazo.codex.spring.data.basic.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Slf4j
@SpringBootApplication
@EntityScan(basePackageClasses = { Record.class })
public class App
{
    public static void main (String [] args)
    {
        log.info("Starting app");
        new SpringApplication(App.class).run(args);
    }
}