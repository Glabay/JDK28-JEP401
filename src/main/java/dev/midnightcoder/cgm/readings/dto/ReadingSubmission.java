package dev.midnightcoder.cgm.readings.dto;

import java.time.LocalDateTime;

public value record ReadingSubmission(
    Long sequenceNumber,
    LocalDateTime capturedAt,
    Double glucoseMgDl
) {}