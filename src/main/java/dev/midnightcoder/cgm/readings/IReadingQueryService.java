package dev.midnightcoder.cgm.readings;

import dev.midnightcoder.cgm.readings.dto.ReadingView;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface IReadingQueryService {

    Optional<ReadingView> findLatest(UUID sensorId);

    ReadingHistoryResult findHistory(
            UUID sensorId,
            Instant from,
            Instant to,
            int limit
    );

}