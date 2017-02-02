package com.github.apycazo.codex.spring.base.lazy;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This service will be provided to Type1 & Type2 components to register themselves on creation.
 */
@Service
public class LazyObserver
{
    private AtomicInteger counter = new AtomicInteger(0);

    public void inc()
    {
        counter.incrementAndGet();
    }

    public int getCount()
    {
        return counter.get();
    }
}
