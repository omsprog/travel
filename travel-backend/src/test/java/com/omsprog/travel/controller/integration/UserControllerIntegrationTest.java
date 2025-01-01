package com.omsprog.travel.controller.integration;

import com.omsprog.travel.testutil.JwtTestUtil;
import com.omsprog.travel.dto.response.UserResponse;
import com.omsprog.travel.dto.response.pagination.UserPage;
import com.omsprog.travel.repository.UserRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    private static String jwtToken;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void setup(@Autowired JwtTestUtil jwtTestUtil) {
        jwtToken = jwtTestUtil.generateMockToken();
    }

    @Test
    @DisplayName("User can be created")
    @Order(1)
    void validUser_whenCreateUser_returnsCreatedUser() throws JSONException {
        // Arrange
        JSONObject validUserRequestJson = new JSONObject();
        validUserRequestJson.put("dni", "WICR970328OPEBR891");
        validUserRequestJson.put("fullName", "Craig Willer");
        validUserRequestJson.put("phoneNumber", "5889816789");
        validUserRequestJson.put("email", "craig@gmail.com");
        validUserRequestJson.put("password", "userPass");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(validUserRequestJson.toString(), headers);

        // Act
        ResponseEntity<UserResponse> createdUserResponseEntity = testRestTemplate
                .postForEntity("/users/signup", request, UserResponse.class);

        UserResponse createdCustomer = createdUserResponseEntity.getBody();

        // Assert
        if(createdCustomer == null)
            fail();

        assertEquals(HttpStatus.OK, createdUserResponseEntity.getStatusCode());
        assertEquals(validUserRequestJson.getString("dni"), createdCustomer.getDni());

        // Clean up
        var customerToBeDeleted = userRepository.findByDni(createdCustomer.getDni()).get();
        userRepository.delete(customerToBeDeleted);
    }

    @Test
    @DisplayName("List of users works")
    @Order(2)
    void validGetRequest_whenGetCustomers_returnsListOfCustomers() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + jwtToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        final int pageSize = 5;

        // Act
        ResponseEntity<UserPage> pageOfFlightsResponseEntity = testRestTemplate
                .exchange(String.format("/users?page=0&size=%s", pageSize), HttpMethod.GET, request, UserPage.class);

        UserPage pageOfCustomers = pageOfFlightsResponseEntity.getBody();

        // Assert
        if(pageOfCustomers == null)
            fail();
        assertEquals(HttpStatus.OK, pageOfFlightsResponseEntity.getStatusCode());
        assertEquals(pageSize, pageOfCustomers.getContent().size());
        assertFalse(pageOfCustomers.isLast());
    }
}