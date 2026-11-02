package dev.midnightcoder.cgm.readings.dto;

import java.time.LocalDateTime;

public record ReadingSubmission(
    Long sequenceNumber,
    LocalDateTime capturedAt,
    Double glucoseMgDl
) {}