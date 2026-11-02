package dev.midnightcoder.cgm.readings.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public value record ReadingAcceptedResponse(
    UUID batchId,
    Integer acceptedCount,
    Integer duplicateCount,
    LocalDateTime acceptedAt
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