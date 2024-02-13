package com.update.ctrl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.p6.model.Project;


@Service

public class P6Service {

 @Value("${p6.api.url}") // configure in application.properties or application.yml
 private String p6ApiUrl;

 @Value("${p6.api.username}")
 private String p6ApiUsername;

 @Value("${p6.api.token}")
 private String p6ApiToken;

 @Autowired
 private RestTemplate restTemplate; // Autowire RestTemplate bean

 public void updateProject(Project project) {
     // Implement logic to make P6 REST API call for updating project start date
     HttpHeaders headers = new HttpHeaders();
     headers.setBearerAuth(p6ApiToken);
     headers.setContentType(MediaType.APPLICATION_JSON);

     // Assuming the P6 API endpoint for updating project start date is "/projects/{projectId}/updateStartDate"
     String updateUrl = p6ApiUrl + "/project?Filter=Id :eq:" + project.getId()+"&Fields=Name,ObjectId";

     // Assuming you have a method to convert Project to JSON format
     String jsonBody = convertProjectToJson(project);

     HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

     // Use RestTemplate to make the actual HTTP call
     ResponseEntity<String> response = restTemplate.exchange(updateUrl, HttpMethod.PUT, requestEntity, String.class);

     // Handle the response as needed
 }

 private String convertProjectToJson(Project project) {
     // Implement logic to convert Project to JSON format
     // You may use a library like Jackson or any other JSON processing library
     // Return the JSON as a String
	 ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        return objectMapper.writeValueAsString(project);
	    } catch (JsonProcessingException e) {
	        // Handle the exception as needed
	        e.printStackTrace();
	        return null;
	    }
 }
}
