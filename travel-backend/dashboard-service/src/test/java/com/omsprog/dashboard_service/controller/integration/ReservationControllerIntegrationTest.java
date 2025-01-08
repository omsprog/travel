package com.omsprog.dashboard_service.controller.integration;

import com.omsprog.dashboard_service.dto.response.pagination.ReservationPage;
import com.omsprog.dashboard_service.testutil.JwtTestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testdata")
class ReservationControllerIntegrationTest {

    private static String jwtToken;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeAll
    static void setup(@Autowired JwtTestUtil jwtTestUtil) {
        jwtToken = jwtTestUtil.generateMockToken();
    }

    @Test
    @DisplayName("List of reservations works")
    void validGetRequest_whenGetReservations_returnsListOfReservations() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + jwtToken);

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