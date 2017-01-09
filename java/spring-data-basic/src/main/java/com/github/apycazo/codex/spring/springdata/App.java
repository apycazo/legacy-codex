package com.github.apycazo.codex.spring.springdata;

import com.github.apycazo.codex.spring.springdata.components.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;

@Slf4j
@SpringBootApplication
@EntityScan(basePackageClasses = { Record.class })
public class App
{
    public static void main (String [] args)
    {
        log.info("Starting app");
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.run(args);
    }
}
