package dev.midnightcoder.cgm.sensors.dto;

import java.time.Instant;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
public record SensorRegistration(
    String serialNumber,
    String manufacturer,
    String model,
    Instant expiresAt
) {}
