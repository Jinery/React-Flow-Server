package com.kychnoo.react_flow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "state_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private LocalDateTime timestamp;
    private String value;
    private Integer intensity;

}
