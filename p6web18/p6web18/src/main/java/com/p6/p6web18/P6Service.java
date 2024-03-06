package com.p6.p6web18;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class P6Service {
	//public Project project;
	 private final RestTemplate restTemplate;

	    @Value("${p6.baseUrl}")
	    private String baseUrl;
	    public P6Service(RestTemplate restTemplate) {
		
			this.restTemplate = restTemplate;
		}

		 private String sessionCookie;
  

      public String login() throws URISyntaxException {
        URI loginUri = UriComponentsBuilder.fromUriString(baseUrl)
		        .path("/login")
		        .queryParam("DatabaseName", "P6EPPM2")
		        .build()
		        .toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "*/*");
		headers.set("authToken", "YWRtaW46YWRtaW4xMjM=");
        //headers.set("Authorization", authToken);


		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(loginUri, HttpMethod.POST, request, String.class);
		List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);

		if (cookies != null && !cookies.isEmpty()) {
		    System.out.print(cookies.get(0));
		    return cookies.get(0);
		} else {
		    throw new RuntimeException("Cookie not found in the response");
		}
    }


        


    public  String createProject(Project project1) throws URISyntaxException {
    	
        String createUrl = baseUrl + "/project"; // Adjust the create project endpoint

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
   headers.set("Content-Type","application/json;charset=UTF-8");

        String cookie = login();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookie);
        headers.set("Cache-Control", "no-cache");
        headers.set("Accept", "*/*");

        Long Id1=project1.getId();
		
        String epsid=project1.getEpsid();
        
	String newStartDate=project1.getName();
	  String requestBody = "[{\"Id\":\"" + Id1 + "\",\"Name\":\"" + newStartDate + "\",\"ParentEPSObjectId\":\"" + epsid + "\"}]";
       HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
       ResponseEntity<String> response = restTemplate.exchange(createUrl, HttpMethod.POST, request, String.class);


        System.out.println("body:"+response.getBody());

		return response.getBody();
    }
        
    
    public String getProjectsByEPSId(Long epsId, Project project2) {
        try {
        	String epsid=project2.getEpsid();
            String getUrl = baseUrl + "/project?Fields=Name&Filter=ParentEPSObjectId :eq: " + epsId;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String cookie = login();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.COOKIE, cookie);
            headers.set("Cache-Control", "no-cache");
            headers.set("Accept", "*/*");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    getUrl, HttpMethod.GET, entity,String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                // Handle other HTTP status codes if needed
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log or handle the exception appropriately
            return null;
        }
    }
   
    public boolean updateProject(Project updatedProject) throws URISyntaxException {
        String updateUrl = baseUrl + "/project"; // Adjust the update project endpoint

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
   headers.set("Content-Type","application/json;charset=UTF-8");

        String cookie = login();
        headers.set(HttpHeaders.COOKIE, cookie);
        headers.set("Cache-Control", "no-cache");
        headers.set("Accept", "*/*");

        // Get objectId and newStartDate from the updateRequest
        Long objectId = updatedProject.getId();
        String newStartDate = updatedProject.getName();

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
            return false;
        }
    }
   


    public void deleteProject(Long projectId) {
        String deleteUrl = baseUrl + "/projects/" + projectId; // Adjust the delete project endpoint

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, sessionCookie);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);
    }





	
}

