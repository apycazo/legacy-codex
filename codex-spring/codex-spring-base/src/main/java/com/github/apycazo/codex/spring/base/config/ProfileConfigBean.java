package com.github.apycazo.codex.spring.base.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "profile.test")
public class ProfileConfigBean
{
    public static final String DEFAULT = "default";

    private String defaultValue = DEFAULT;
    private String firstProfileValue = DEFAULT;

    @PostConstruct
    protected void logContent ()
    {
        log.info("{} content: {}", this.getClass().getSimpleName(), this);
    }

}
