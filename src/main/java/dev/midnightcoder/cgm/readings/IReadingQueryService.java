package dev.midnightcoder.cgm.readings;

import dev.midnightcoder.cgm.readings.dto.ReadingHistoryResult;
import dev.midnightcoder.cgm.readings.dto.ReadingView;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface IReadingQueryService {

    Optional<ReadingView> findLatest(UUID sensorId);

    ReadingHistoryResult findHistory(
            UUID sensorId,
            LocalDateTime from,
            LocalDateTime to,
            int limit
    );

    Optional<Double> calculateAverageOverWeek(UUID sensorId);

    Optional<Double> calculateAverageOverMonth(UUID sensorId);

    Optional<Double> calculateAverageOverThreeMonths(UUID sensorId);

}