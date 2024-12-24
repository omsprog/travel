package com.omsprog.travel.controller.integration;

import com.omsprog.travel.dto.response.CustomerResponse;
import com.omsprog.travel.dto.response.pagination.CustomerPage;
import com.omsprog.travel.repository.CustomerRepository;
import org.json.JSONException;
import org.json.JSONObject;
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
class CustomerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CustomerRepository customerRepository;

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
        ResponseEntity<CustomerResponse> createdUserResponseEntity = testRestTemplate
                .postForEntity("/users/signup", request, CustomerResponse.class);

        CustomerResponse createdCustomer = createdUserResponseEntity.getBody();

        // Assert
        if(createdCustomer == null)
            fail();

        assertEquals(HttpStatus.OK, createdUserResponseEntity.getStatusCode());
        assertEquals(validUserRequestJson.getString("dni"), createdCustomer.getDni());

        // Clean up
        var customerToBeDeleted = customerRepository.findByDni(createdCustomer.getDni()).get();
//        customerRepository.delete(customerToBeDeleted);
    }

    @Test
    @DisplayName("List of users works")
    @Order(2)
    void validGetRequest_whenGetCustomers_returnsListOfCustomers() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);
        final int pageSize = 5;

        // Act
        ResponseEntity<CustomerPage> pageOfFlightsResponseEntity = testRestTemplate
                .exchange(String.format("/users?page=0&size=%s", pageSize), HttpMethod.GET, request, CustomerPage.class);

        CustomerPage pageOfCustomers = pageOfFlightsResponseEntity.getBody();

        // Assert
        if(pageOfCustomers == null)
            fail();
        assertEquals(HttpStatus.OK, pageOfFlightsResponseEntity.getStatusCode());
        assertEquals(pageSize, pageOfCustomers.getContent().size());
        assertFalse(pageOfCustomers.isLast());
    }
}