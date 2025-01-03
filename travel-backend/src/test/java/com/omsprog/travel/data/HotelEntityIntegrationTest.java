package com.omsprog.travel.data;

import com.omsprog.travel.entity.jpa.HotelEntity;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
@Tag("commit-stage")
public class HotelEntityIntegrationTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void whenCustomerEntity_whenValidCustomerDetailsProvided_shouldReturnStoredCustomerDetails() {
        // Arrange
        HotelEntity hotelEntity = HotelEntity
                .builder()
                .name("Heaven")
                .address("Palm Beach, Miami")
                .rating(5)
                .price(BigDecimal.valueOf(4000))
                .build();

        // Act
        HotelEntity storedHotelEntity = testEntityManager.persistAndFlush(hotelEntity);

        // Assert
        assertTrue(storedHotelEntity.getId() > 0);
        assertEquals("Heaven", storedHotelEntity.getName());
        assertEquals("Palm Beach, Miami", storedHotelEntity.getAddress());
        assertEquals(5, storedHotelEntity.getRating());
        assertEquals(BigDecimal.valueOf(4000), storedHotelEntity.getPrice());
    }
}
