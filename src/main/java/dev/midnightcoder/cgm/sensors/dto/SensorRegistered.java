package dev.midnightcoder.cgm.sensors.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
public value record SensorRegistered(
    UUID sensorId,
    String serialNumber,
    LocalDateTime registeredAt
) {}