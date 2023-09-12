package com.example.demo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Route;
import com.example.demo.repo.RouteRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RouteController {
	
	@Autowired
	private RouteRepo repo;

	
    @GetMapping(value = "/jsonResponse")
    public ResponseEntity<?> getRequest() {
        try {
            // Load the JSON file from the specified path
            File jsonFile = new File("C:\\Users\\249175\\Desktop\\routes.json");

            // Create an instance of the ObjectMapper to work with JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON data from the file into a JsonNode
            JsonNode jsonArray = objectMapper.readTree(jsonFile);

            // Create a list to store the airline details
            List<Route> routeDetails = new ArrayList<>();

            // Iterate through the JSON array and deserialize each object into an Airline instance
            for (JsonNode object : jsonArray) {
                Route route = objectMapper.treeToValue(object, Route.class);
                repo.save(route);
                routeDetails.add(route);
            }

            // Return the list of airline details in the response
            return new ResponseEntity<>(routeDetails, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // If there's an error, return a bad request response
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
