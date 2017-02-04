package com.github.apycazo.codex.spring.rest.persistence.basic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Note that, by default, @DataJpaTest does the following:
 * <ul>
 *     <li>Configure an in-memory database.</li>
 *     <li>CAuto-configure Hibernate, Spring Data and the DataSource.</li>
 *     <li>Perform an @EntityScan</li>
 *     <li>Turn on SQL logging</li>
 * </ul>
 */
@DataJpaTest(showSql = false)
@RunWith(SpringRunner.class)
public class RecordRepositoryTest
{
    @Autowired
    private RecordRepository recordRepository;

    /**
     * Utility method to generate a few dummy records
     * @param quantity How many records shall be created
     * @return The generated record list
     */
    private List<Record> createDummyRecords(int quantity)
    {
        List<Record> records = new LinkedList<>();
        for (long i = 1; i <= quantity; i++) {
            records.add(Record.builder()
                    .name("record-" + i)
                    .updates(-1)
                    .build()
            );
        }

        return records;
    }

    /**
     * Clear the persistence before running any test
     */
    @Before
    public void clearPersistence ()
    {
        recordRepository.deleteAll();
        assertEquals("Repository should not have records", 0, recordRepository.count());
    }

    @Test
    public void onDeleteAll()
    {
        assertEquals("Repository should not have records", 0, recordRepository.count());
        // Add a few records
        List<Record> recordList = createDummyRecords(3);
        recordList.forEach(System.out::println);
        recordRepository.save(recordList);
        assertEquals(recordList.size(), recordRepository.count());
        // this check is actually duplicated on @After, but left here as reference
        recordRepository.deleteAll();
        assertEquals("Repository should not have records", 0, recordRepository.count());
    }

    @Test
    public void onSaveRecord ()
    {
        // Create a record to persist
        Record record = createDummyRecords(1).stream().findFirst().orElse(null);
        assertNotNull("Record generated is null", record);
        // Set a different 'updates' value, to check if 'BeforeCreate' is working
        record.setUpdates(100);
        Record persistedRecord = recordRepository.save(record);
        // Persistent must not be null
        assertNotNull("Persisted record is null", persistedRecord);
        // And 'updates' value must be 0
        assertEquals(0, persistedRecord.getUpdates().longValue());
    }

    /**
     * Test the @PrePersist and @PreUpdate annotations. Please note that the value to update should be
     * fetched again after the first save to call the @PreUpdate annotated method.
     */
    @Test
    public void onUpdateRecord ()
    {
        Record record = createDummyRecords(1).stream().findFirst().orElse(null);
        assertNotNull("Record generated is null", record);

        record = recordRepository.save(record);
        assertNotNull("Saved record has no id", record.getId());
        assertEquals("Saved record 'updates' should be 0", 0, record.getUpdates().longValue());

        // This does NOT work (updates is still 0)
        record.setName("updated-id-" + record.getId() + "-first");
        record = recordRepository.save(record);
        assertEquals("Saved record 'updates' should still be 0", 0, record.getUpdates().longValue());

        // But fetching again does...
        Record fetchedRecord = recordRepository.findOne(record.getId());
        fetchedRecord.setName("updated-id-" + fetchedRecord.getId() + "-second");
        fetchedRecord = recordRepository.save(fetchedRecord);
        assertEquals("Saved record 'updates' should now be 1", 0, fetchedRecord.getUpdates().longValue());

    }
}