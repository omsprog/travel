package com.omsprog.travel.repository;

import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MongoRepositoryTest {
    @Autowired
    AppUserRepository appUserRepository;

    @Test
    public void testingMongoRepository() {
        assertEquals("VIKI771012HMCRG093", this.appUserRepository.findByUsername("ragnar777").get().getDni());
    }
}