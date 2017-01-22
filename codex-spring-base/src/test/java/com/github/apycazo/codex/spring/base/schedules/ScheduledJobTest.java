package com.github.apycazo.codex.spring.base.schedules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ScheduledJobTest
{
    @Autowired
    ScheduledJob job;

    @Test
    public void checkMultipleExecutionsForTheJob () throws InterruptedException
    {
        int initialValue = job.getCurrentValue();
        // if I ask right away, it gives the same value
        assertEquals("Values are not the same", initialValue, job.getCurrentValue());
        // wait for the next tick (a bit more than the given value).
        Thread.sleep(ScheduledJob.TICK_IN_MS + 50);
        // assert new value is different
        assertNotEquals("Values are still the same", initialValue, job.getCurrentValue());
    }
}