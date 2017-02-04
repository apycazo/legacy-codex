package com.github.apycazo.codex.spring.base.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CountServiceTest
{
    @Autowired
    private CountService countService;
    @Autowired
    private CaffeineCacheManager caffeineCacheManager;

    @Test
    public void timeBasedCacheTest () throws InterruptedException
    {
        // make sure the cache has been created
        assertNotNull("Cache manager not found", caffeineCacheManager);
        assertNotNull("Cache manager for 'timed-cache' not found", caffeineCacheManager.getCache("timed-cache"));

        int v1 = countService.getCount();
        Thread.sleep(500);
        int v2 = countService.getCount();
        assertEquals("Count value is not the same", v1, v2);
        Thread.sleep(500);
        int v3 = countService.getCount();
        assertEquals("Count value is not the same", v1, v3);
        Thread.sleep(2500);
        int v4 = countService.getCount();
        assertNotEquals("Count value is the same", v1, v4);
    }

    @Test
    public void customEvictionBasedCacheTest () throws InterruptedException
    {
        // make sure the cache has been created
        assertNotNull("Cache manager not found", caffeineCacheManager);
        assertNotNull("Cache manager for 'timed-cache' not found", caffeineCacheManager.getCache("timed-cache"));

        int v1 = countService.getCount();
        Thread.sleep(500);
        int v2 = countService.getCount();
        assertEquals("Count value is not the same", v1, v2);
        Thread.sleep(500);
        int v3 = countService.getCount();
        assertEquals("Count value is not the same", v1, v3);
        // Evict the values
        countService.evict();
        int v4 = countService.getCount();
        assertNotEquals("Count value is the same", v1, v4);
    }
}