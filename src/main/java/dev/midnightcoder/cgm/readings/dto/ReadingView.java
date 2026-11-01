package dev.midnightcoder.cgm.readings.dto;

import java.time.Instant;
import java.util.UUID;

public record ReadingView(
    UUID id,
    UUID sensorId,
    long sequenceNumber,
    Instant capturedAt,
    int glucoseMgDl
) {}