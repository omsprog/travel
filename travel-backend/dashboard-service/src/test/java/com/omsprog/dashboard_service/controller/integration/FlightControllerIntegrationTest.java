package com.omsprog.dashboard_service.controller.integration;

import com.omsprog.dashboard_service.dto.response.FlightResponse;
import com.omsprog.dashboard_service.dto.response.pagination.FlightPage;
import com.omsprog.dashboard_service.testutil.JwtTestUtil;
import com.omsprog.dashboard_service.util.AeroLine;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
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
class FlightControllerIntegrationTest {

    private static String jwtToken;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeAll
    static void setup(@Autowired JwtTestUtil jwtTestUtil) {
        jwtToken = jwtTestUtil.generateMockToken();
    }

    @Test
    @DisplayName("Flight can be created")
    @Order(1)
    void validFlight_whenCreateFlight_returnsCreatedFlight() throws JSONException {
        // Arrange
        JSONObject validFlightRequestJson = new JSONObject();
        validFlightRequestJson.put("originLat", 80.9999);
        validFlightRequestJson.put("originLng", 88.8888);
        validFlightRequestJson.put("destinationLat", 22.2222);
        validFlightRequestJson.put("destinationLng", 11.1111);
        validFlightRequestJson.put("originName", "Mexico");
        validFlightRequestJson.put("destinationName", "Russia");
        validFlightRequestJson.put("price", 90);
        validFlightRequestJson.put("aeroLine", AeroLine.aero_gold.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + jwtToken);

        HttpEntity<String> request = new HttpEntity<>(validFlightRequestJson.toString(), headers);

        // Act
        ResponseEntity<FlightResponse> createdFlightResponseEntity = testRestTemplate
                .postForEntity("/flights", request, FlightResponse.class);

        FlightResponse createdFlight = createdFlightResponseEntity.getBody();

        // Assert
        if(createdFlight == null)
            fail();

        assertEquals(HttpStatus.OK, createdFlightResponseEntity.getStatusCode());
        assertEquals(validFlightRequestJson.getString("originName"), createdFlight.getOriginName());
    }

    @Test
    @DisplayName("List of flights works")
    @Order(2)
    void validGetRequest_whenGetFlights_returnsListOfFlights() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + jwtToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        final int pageSize = 5;

        // Act
        ResponseEntity<FlightPage> pageOfFlightsResponseEntity = testRestTemplate
                .exchange(String.format("/flights?page=0&size=%s", pageSize), HttpMethod.GET, request, FlightPage.class);

        FlightPage pageOfFlights = pageOfFlightsResponseEntity.getBody();

        // Assert
        if(pageOfFlights == null)
            fail();
        assertEquals(HttpStatus.OK, pageOfFlightsResponseEntity.getStatusCode());
        assertEquals(pageSize, pageOfFlights.getContent().size());
        assertFalse(pageOfFlights.isLast());
    }
}