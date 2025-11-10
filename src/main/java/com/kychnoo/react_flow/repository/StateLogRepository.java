package com.kychnoo.react_flow.repository;

import com.kychnoo.react_flow.model.StateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StateLogRepository extends JpaRepository<StateLog, Long> {
    List<StateLog> findByUserIdAndTimestampBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
