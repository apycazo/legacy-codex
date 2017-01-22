package com.github.apycazo.codex.spring.base.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CountService
{
    private AtomicInteger accessCount = new AtomicInteger(0);

    @Cacheable("timed-cache")
    public Integer getCount ()
    {
        return accessCount.incrementAndGet();
    }

    @CacheEvict(cacheNames = "timed-cache", allEntries = true)
    public void evict ()
    {
        // force eviction of all entries
    }
}
