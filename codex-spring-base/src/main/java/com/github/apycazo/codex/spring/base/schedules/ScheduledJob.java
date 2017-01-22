package com.github.apycazo.codex.spring.base.schedules;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScheduledJob
{
    public static final long TICK_IN_MS = 500L;

    private AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = TICK_IN_MS)
    protected void tick ()
    {
        count.incrementAndGet();
    }

    public int getCurrentValue ()
    {
        return count.get();
    }
}
