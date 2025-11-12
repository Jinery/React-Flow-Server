package com.kychnoo.react_flow.dto.request.logs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Model for body request to get a log data.

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRequest {
    private Long userId;
    private LocalDateTime start;
    private LocalDateTime end;
}
