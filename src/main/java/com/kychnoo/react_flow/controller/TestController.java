package com.kychnoo.react_flow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller for test secured endpoint.
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping // Send get request to /api/test using by Bearer token.
    private ResponseEntity<String> getTestMapping() {
        return ResponseEntity.ok("Successful");
    }
}
