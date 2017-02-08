package com.github.apycazo.codex.spring.rest.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AsyncServiceTest
{
    @Autowired
    private AsyncService service;

    @Test
    public void testTimeToComplete() throws Exception
    {
        List<Integer> values = Arrays.asList(1,2,3,4);
        List<Future<Integer>> futureResults = new LinkedList<>();

        int nanoStart = Instant.now().getNano();
        values.forEach(value -> {
            try {
                service.runOperation(value);
                log.info("Non async op completed for value: {}", value);
            } catch (InterruptedException e) {
                fail("Thread threw an exception: " + e.getMessage());
            }
        });

        int elapsedVanilla = Instant.now().getNano() - nanoStart;
        nanoStart = Instant.now().getNano();
        // Note: since the configured executor has a core pool of 4, all values will be processed at the same time
        values.forEach(value -> {
            try {
                futureResults.add(service.runAsyncOperation(value));
            } catch (InterruptedException e) {
                fail("Thread threw an exception: " + e.getMessage());
            }
        });

        // wait for all futures
        futureResults.forEach(future -> {
            try {
                log.info("Completed future with result: {}", future.get());
            } catch (InterruptedException | ExecutionException e) {
                fail("Thread threw an exception: " + e.getMessage());
            }
        });
        int elapsedAsync = Instant.now().getNano() - nanoStart;

        log.info("Results: vanilla={}, async={}", elapsedVanilla, elapsedAsync);
        // Result should be faster
        assertTrue(elapsedAsync < elapsedVanilla);
    }

}