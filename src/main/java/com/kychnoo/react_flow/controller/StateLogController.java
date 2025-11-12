package com.kychnoo.react_flow.controller;

import com.kychnoo.react_flow.dto.request.logs.LogRequest;
import com.kychnoo.react_flow.model.StateLog;
import com.kychnoo.react_flow.repository.StateLogRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class StateLogController {
    @Autowired
    private StateLogRepository stateLogRepository;

    @PostMapping
    public ResponseEntity<StateLog> createLog(@RequestBody StateLog log) {
        if(log.getTimestamp() == null) {
            log.setTimestamp(LocalDateTime.now());
        }
        StateLog savedLog = stateLogRepository.save(log); // Save log to database.

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLog.getId())
                .toUri(); // Generate created URI using current uri path and saved log id.

        return ResponseEntity.created(location).body(savedLog);
    }

    // Endpoint to get graph points as List.
    @PostMapping("/graph")
    public ResponseEntity<List<StateLog>> getLogsGraph(@RequestBody LogRequest request) {

        // Get all params from request.
        Long userId = request.getUserId();
        LocalDateTime start = request.getStart();
        LocalDateTime end = request.getEnd();

        List<StateLog> logs = stateLogRepository.findByUserIdAndTimestampBetween(userId, start, end);
        return ResponseEntity.ok(logs);
    }
}
