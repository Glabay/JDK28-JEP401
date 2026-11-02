package dev.midnightcoder.cgm.sensors.internal;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "serial_number", nullable = false, length = 100)
    private String serialNumber;

    @Column(nullable = false, length = 100)
    private String manufacturer;

    @Column(nullable = false, length = 100)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SensorStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime expiresAt;

    private LocalDateTime lastSeenAt;

    @Version
    private long version;

    protected Sensor() {
    }

    private Sensor(
        String serialNumber,
        String manufacturer,
        String model,
        LocalDateTime expiresAt
    ) {
        this.serialNumber = Objects.requireNonNull(serialNumber);
        this.manufacturer = Objects.requireNonNull(manufacturer);
        this.model = Objects.requireNonNull(model);
        this.expiresAt = expiresAt;
        this.status = SensorStatus.REGISTERED;
        this.registeredAt = LocalDateTime.now();
    }

    public static Sensor register(
        String serialNumber,
        String manufacturer,
        String model,
        LocalDateTime expiresAt
    ) {
        return new Sensor(
            serialNumber,
            manufacturer,
            model,
            expiresAt
        );
    }

    public void activate() {
        if (status != SensorStatus.REGISTERED &&
            status != SensorStatus.INACTIVE
        ) throw new IllegalStateException("Sensor cannot be activated from status: " + status);

        status = SensorStatus.ACTIVE;
        activatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        if (status != SensorStatus.ACTIVE)
            throw new IllegalStateException("Only active sensors can be deactivated");

        status = SensorStatus.INACTIVE;
    }

    public void revoke() {
        status = SensorStatus.REVOKED;
    }

    public void markSeen(LocalDateTime receivedAt) {
        if (status != SensorStatus.ACTIVE)
            throw new IllegalStateException("Sensor is not active");

        Objects.requireNonNull(receivedAt);

        if (lastSeenAt == null ||
            receivedAt.isAfter(lastSeenAt)
        ) lastSeenAt = receivedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public SensorStatus getStatus() {
        return status;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDateTime getActivatedAt() {
        return activatedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getLastSeenAt() {
        return lastSeenAt;
    }

    public long getVersion() {
        return version;
    }

    public enum SensorStatus {
        REGISTERED,
        ACTIVE,
        INACTIVE,
        EXPIRED,
        REVOKED
    }
}
