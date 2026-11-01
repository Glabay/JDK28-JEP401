package dev.midnightcoder.cgm.readings.dto;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record ReadingHistoryResult(
    UUID sensorId,
    Instant from,
    Instant to,
    int limit,
    List<ReadingView> readings
) {
    public ReadingHistoryResult {
        Objects.requireNonNull(sensorId);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        Objects.requireNonNull(readings);
    }
}

