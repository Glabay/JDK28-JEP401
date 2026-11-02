package dev.midnightcoder.cgm.readings.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public value record ReadingBatchResult(
    UUID batchId,
    Integer acceptedCount,
    Integer duplicateCount,
    LocalDateTime acceptedAt
) {
    public ReadingBatchResult {
        Objects.requireNonNull(batchId);
        Objects.requireNonNull(acceptedAt);

        if (acceptedCount < 0 || duplicateCount < 0) {
            throw new IllegalArgumentException("Reading counts cannot be negative");
        }
    }
}