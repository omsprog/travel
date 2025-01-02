package com.omsprog.travel.validation;

import com.omsprog.travel.dto.request.HotelRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

@Tag("commit-stage")
public class HotelValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    static HotelRequest getValidHotelRequest() {
        return HotelRequest.builder()
                .name("Palace")
                .price(BigDecimal.ONE)
                .address("Las Vegas")
                .rating(4)
                .build();
    }

    @Test
    void validHotelRequest_whenAllFieldsCorrect_validationSucceeds() {
        HotelRequest hotelRequest = getValidHotelRequest();
        Set<ConstraintViolation<HotelRequest>> violations = validator.validate(hotelRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidHotelRequest_whenNameIsNotProvided_returnsValidationError() {
        HotelRequest hotelRequest = getValidHotelRequest();
        hotelRequest.setName(null);
        Set<ConstraintViolation<HotelRequest>> violations = validator.validate(hotelRequest);
        assertEquals(1, violations.size());
        assertEquals("Hotel name is mandatory", violations.iterator().next().getMessage());
    }
}
