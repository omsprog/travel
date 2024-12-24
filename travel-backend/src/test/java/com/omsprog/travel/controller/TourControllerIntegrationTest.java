package com.omsprog.travel.controller;

import com.omsprog.travel.dto.response.TourSummaryResponse;
import com.omsprog.travel.dto.response.pagination.TourSummaryPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TourControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("List of tours works")
    void validGetRequest_whenGetTours_returnsListOfTours() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);
        final int pageSize = 5;

        // Act
        ResponseEntity<TourSummaryPage> pageOfTourSummaryResponseEntity = testRestTemplate
                .exchange(String.format("/tours?page=0&size=%s", pageSize), HttpMethod.GET, request, TourSummaryPage.class);

        TourSummaryPage pageOfTourSummary = pageOfTourSummaryResponseEntity.getBody();

        // Assert
        if(pageOfTourSummary == null)
            fail();

        TourSummaryResponse tourSummaryResponseFirst = pageOfTourSummary.getContent().get(0);
        TourSummaryResponse tourSummaryResponseSecond = pageOfTourSummary.getContent().get(1);

        assertEquals(HttpStatus.OK, pageOfTourSummaryResponseEntity.getStatusCode());
        assertEquals("Honey Moon", tourSummaryResponseFirst.getName());
        assertEquals("Magical Tour", tourSummaryResponseSecond.getName());
    }
}