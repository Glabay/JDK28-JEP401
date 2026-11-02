package dev.midnightcoder.cgm.sensors.dto;

import java.time.LocalDateTime;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
public value record SensorRegistration(
    String serialNumber,
    String manufacturer,
    String model,
    LocalDateTime expiresAt
) {}
