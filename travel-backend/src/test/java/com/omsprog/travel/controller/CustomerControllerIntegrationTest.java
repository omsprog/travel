package com.omsprog.travel.controller;

import com.omsprog.travel.dto.response.pagination.CustomerPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("List of customers works")
    void validGetRequest_whenGetCustomers_returnsListOfCustomers() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);
        final int pageSize = 5;

        // Act
        ResponseEntity<CustomerPage> pageOfFlightsResponseEntity = testRestTemplate
                .exchange(String.format("/customers?page=0&size=%s", pageSize), HttpMethod.GET, request, CustomerPage.class);

        CustomerPage pageOfCustomers = pageOfFlightsResponseEntity.getBody();

        // Assert
        if(pageOfCustomers == null)
            fail();
        assertEquals(HttpStatus.OK, pageOfFlightsResponseEntity.getStatusCode());
        assertEquals(pageSize, pageOfCustomers.getContent().size());
        assertFalse(pageOfCustomers.isLast());
    }
}