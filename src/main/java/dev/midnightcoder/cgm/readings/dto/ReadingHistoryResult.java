package dev.midnightcoder.cgm.readings.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public value record ReadingHistoryResult(
    UUID sensorId,
    LocalDateTime from,
    LocalDateTime to,
    Integer limit,
    List<ReadingView> readings
) {
    public ReadingHistoryResult {
        Objects.requireNonNull(sensorId);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        Objects.requireNonNull(readings);
    }
}

