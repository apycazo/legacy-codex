package com.github.apycazo.codex.spring.data.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TestBench
{
    @Autowired
    private RecordRepository repository;

    @PostConstruct
    protected void runTest()
    {
        log.info("Running test");

        Record record = Record.builder()
                .id(0L)
                .name("test")
                .updates(-1)
                .build();

        Record persistedRecord = repository.save(record);

        log.info("Persisted : '{}'", persistedRecord);

        // Do some updates
        for (int i = 0; i < 3; i++) {
            persistedRecord.setName("test-" + i);
            persistedRecord = repository.save(persistedRecord);
        }

        log.info("Persisted (final): '{}'", persistedRecord);
    }
}
