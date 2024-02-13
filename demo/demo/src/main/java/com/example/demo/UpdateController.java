package com.example.demo;

//import java.util.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class UpdateController {
	public static final Logger log = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    private UpdateService updateService;
    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok(UpdateService.login());
    }

  
    
    @PostMapping("/updateStartDate")
    public ResponseEntity<String> updateStartDate(@RequestBody UpdateRequest updateRequest) {
       // int objectId = UpdateRequest.getObjectId();
       // String newStartDate = updateRequest.getNewStartDate();
        //String authToken = updateRequest.getToken();
    	 try {
        boolean updateResult = updateService.updateStartDate(updateRequest);

        //boolean updateResult = updateService.updateStartDate(objectId, newStartDate);

        if (updateResult) {
            return ResponseEntity.ok("Start date updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update start date");
        }
    	 }
    catch (Exception e) {
        // Log the exception or handle it as needed
        e.printStackTrace();
        return ResponseEntity.status(500).body("Internal Server Error");
    }
    }

}

