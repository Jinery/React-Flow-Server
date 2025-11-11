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
    @Column(name = "state_value")
    private String value;

    @Column(name = "intensity")
    private Integer intensity;

}
