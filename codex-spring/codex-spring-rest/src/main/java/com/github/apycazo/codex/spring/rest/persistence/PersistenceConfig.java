package com.github.apycazo.codex.spring.rest.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EntityScan(basePackageClasses = { PersistenceConfig.class })
public class PersistenceConfig
{
}
