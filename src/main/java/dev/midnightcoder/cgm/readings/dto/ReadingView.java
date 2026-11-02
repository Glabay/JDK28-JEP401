package dev.midnightcoder.cgm.readings.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public value record ReadingView(
    UUID id,
    UUID sensorId,
    Long sequenceNumber,
    LocalDateTime capturedAt,
    Double glucoseMgDl
) {}