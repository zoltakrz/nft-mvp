package com.capgemini.middleware.adapters.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthAdapter {
    @GetMapping(value = "/health")
    public ResponseEntity<String> getHealthStatus() {
        return new ResponseEntity<>("up", HttpStatus.OK);
    }
}
