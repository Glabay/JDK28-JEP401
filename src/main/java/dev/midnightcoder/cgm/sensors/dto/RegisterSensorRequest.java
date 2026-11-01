package dev.midnightcoder.cgm.sensors.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
public record RegisterSensorRequest(

    @NotBlank(message = "Serial number is required")
    @Size(max = 100)
    String serialNumber,

    @NotBlank(message = "Manufacturer is required")
    @Size(max = 100)
    String manufacturer,

    @NotBlank(message = "Model is required")
    @Size(max = 100)
    String model,

    @Future(message = "Expiration date must be in the future")
    Instant expiresAt

) {
    public SensorRegistration toRegistration() {
        return new SensorRegistration(serialNumber, manufacturer, model, expiresAt);
    }
}