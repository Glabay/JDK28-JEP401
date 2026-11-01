package dev.midnightcoder.cgm.readings.dto;

import java.time.Instant;
import java.util.UUID;

public record ReadingAcceptedResponse(
    UUID batchId,
    int acceptedCount,
    int duplicateCount,
    Instant acceptedAt
) {

    public static ReadingAcceptedResponse from(ReadingBatchResult result) {
        return new ReadingAcceptedResponse(
            result.batchId(),
            result.acceptedCount(),
            result.duplicateCount(),
            result.acceptedAt()
        );
    }
}