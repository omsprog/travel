package com.omsprog.travel.controller;

import com.omsprog.travel.dto.response.pagination.ReservationPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("List of reservations works")
    void validGetRequest_whenGetReservations_returnsListOfReservations() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);
        final int pageSize = 5;

        // Act
        ResponseEntity<ReservationPage> pageOfReservationsResponseEntity = testRestTemplate
                .exchange(String.format("/reservations?page=0&size=%s", pageSize), HttpMethod.GET, request, ReservationPage.class);

        ReservationPage pageOfReservations = pageOfReservationsResponseEntity.getBody();

        // Assert
        if(pageOfReservations == null)
            fail();
        assertEquals(HttpStatus.OK, pageOfReservationsResponseEntity.getStatusCode());
        assertEquals(pageSize, pageOfReservations.getContent().size());
        assertFalse(pageOfReservations.isLast());
    }
}