package com.github.apycazo.codex.spring.base.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig
{
    // This is only to enable the cache without polluting App.class
}
