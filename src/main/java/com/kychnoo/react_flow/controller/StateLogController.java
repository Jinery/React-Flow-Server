package com.kychnoo.react_flow.controller;

import com.kychnoo.react_flow.model.StateLog;
import com.kychnoo.react_flow.repository.StateLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class StateLogController {
    @Autowired
    private StateLogRepository stateLogRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateLog createLog(@RequestBody StateLog log) {
        if(log.getTimestamp() == null) {
            log.setTimestamp(LocalDateTime.now());
        }
        return stateLogRepository.save(log);
    }

    @GetMapping
    public List<StateLog> getLogsGraph(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return stateLogRepository.findByUserIdAndTimestampBetween(userId, start, end);
    }
}
