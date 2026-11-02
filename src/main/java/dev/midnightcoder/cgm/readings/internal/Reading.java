package dev.midnightcoder.cgm.readings.internal;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-11-01
 */
@Entity
@Table(name = "readings")
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID sensorId;

    private Long sequenceNumber;

    private Double glucoseMgDl;

    private LocalDateTime capturedAt;
    private LocalDateTime acceptedAt;

    UUID getId() {
        return id;
    }

    UUID getSensorId() {
        return sensorId;
    }

    Long getSequenceNumber() {
        return sequenceNumber;
    }

    Double getGlucoseMgDl() {
        return glucoseMgDl;
    }

    LocalDateTime getCapturedAt() {
        return capturedAt;
    }

    LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    void setSensorId(UUID sensorId) {
        this.sensorId = sensorId;
    }

    void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    void setGlucoseMgDl(Double glucoseMgDl) {
        this.glucoseMgDl = glucoseMgDl;
    }

    void setCapturedAt(LocalDateTime capturedAt) {
        this.capturedAt = capturedAt;
    }

    void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }
}
