package com.example.demo;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class UpdateService {

    private static final String P6_BASE_URL = "http://localhost:8206/p6ws/restapi";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";
    static String userCredentials = USERNAME + ":" + PASSWORD;
	static String base64Credentials = jakarta.xml.bind.DatatypeConverter.printBase64Binary(userCredentials.getBytes());
	static String basicAuth = base64Credentials;
    private static String authToken=basicAuth;

    
    
    public boolean updateStartDate(UpdateRequest updateRequest) {
        String updateUrl = P6_BASE_URL + "/project";  // Adjust the URL according to your P6 API

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.set("Authorization", "Bearer " + authToken); // Commented out because authToken is not used here
        headers.set("Cache-Control", "no-cache");
        headers.set("Accept", "*/*");

        // Get the cookie
        String cookie = login();
        headers.set("Cookie", cookie);

        // Get objectId and newStartDate from the updateRequest
        int objectId = updateRequest.getObjectId();
        String newStartDate = updateRequest.getNewStartDate();

        // Check if newStartDate is null and set a default value
        if (newStartDate == null) {
            newStartDate = "2024-03-01T00:00:00"; // Set your default date here
        }

        // Create the request body with objectId and newStartDate
        String requestBody = "[{\"ObjectId\":\"" + objectId + "\",\"PlannedStartDate\":\"" + newStartDate + "\"}]";

        // Create the HTTP entity with headers and request body
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            // Use PUT method for update (change to POST if your API requires it)
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(updateUrl, HttpMethod.PUT, request, String.class);

            // Handle the response from the external service
            if (response.getStatusCode() == HttpStatus.OK) {
                // Assuming the response contains a success indicator
                // Adjust this based on the actual response structure from the external service
                System.out.println("Updating start date for objectId: " + objectId + " to: " + newStartDate);
                System.out.println("body:"+response.getBody().toLowerCase());
                return "true".equals(response.getBody().toLowerCase());
            } else {
                // Handle other HTTP status codes if needed
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log or handle the exception appropriately
            return false;
        }
    }

    
   
   
    public static String login() {
        String authUrl = P6_BASE_URL + "/login?DatabaseName=P6EPPM2";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
      
            headers.set("Authorization", authToken);
        
        headers.set("authToken", "YWRtaW46YWRtaW4xMjM=");

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(authUrl, HttpMethod.POST, request, String.class);
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (cookies != null && !cookies.isEmpty()) {
        	System.out.print( cookies.get(0));
            return cookies.get(0);  // Returning the first cookie; adjust as needed
        } else {
            throw new RuntimeException("Cookie not found in the response");
        }
    
    }


 
}