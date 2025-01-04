package com.omsprog.travel.controller.integration;

import com.omsprog.travel.dto.response.pagination.TicketPage;
import com.omsprog.travel.testutil.JwtTestUtil;
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
@ActiveProfiles({"integration", "testdata"})
class TicketControllerIntegrationTest {

    private static String jwtToken;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeAll
    static void setup(@Autowired JwtTestUtil jwtTestUtil) {
        jwtToken = jwtTestUtil.generateMockToken();
    }

    @Test
    @DisplayName("List of tickets works")
    void validGetRequest_whenGetTickets_returnsListOfTickets() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + jwtToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        final int pageSize = 5;

        // Act
        ResponseEntity<TicketPage> pageOfTicketsResponseEntity = testRestTemplate
                .exchange(String.format("/tickets?page=0&size=%s", pageSize), HttpMethod.GET, request, TicketPage.class);

        TicketPage pageOfTickets = pageOfTicketsResponseEntity.getBody();

        // Assert
        if(pageOfTickets == null)
            fail();
        assertEquals(HttpStatus.OK, pageOfTicketsResponseEntity.getStatusCode());
        assertEquals(pageSize, pageOfTickets.getContent().size());
        assertFalse(pageOfTickets.isLast());
    }
}