package com.github.apycazo.codex.spring.rest.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Service
public class AsyncService
{
    @Async
    public Future<Integer> runAsyncOperation (Integer value) throws InterruptedException
    {
        Thread.sleep(1000L);
        return new AsyncResult<>(value * 1000);
    }

    public Integer runOperation (Integer value) throws InterruptedException
    {
        Thread.sleep(1000L);
        return value * 1000;
    }
}
