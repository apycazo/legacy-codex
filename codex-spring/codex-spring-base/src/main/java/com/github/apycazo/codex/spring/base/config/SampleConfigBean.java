package com.github.apycazo.codex.spring.base.config;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@ToString
@Component
@ConfigurationProperties(prefix = "config")
public class SampleConfigBean
{
    private String string = "defaultStringValue";
    private List<String> list = new LinkedList<>();
    private Map<String,Object> map = new HashMap<>();

    @Data // getter & setter methods are required
    public static class InnerElement
    {
        private Integer number = 0;
        private Boolean valid = false;
    }

    private InnerElement inner = new InnerElement();
    private String valueAsDefault = "default";

    @PostConstruct
    protected void logContent ()
    {
        log.info("{} content: {}", this.getClass().getSimpleName(), this.toString());
    }
}
