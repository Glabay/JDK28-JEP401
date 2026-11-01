package dev.midnightcoder.cgm.readings.dto;

import java.time.Instant;

public record ReadingSubmission(
    long sequenceNumber,
    Instant capturedAt,
    int glucoseMgDl
) {}