package dev.midnightcoder.cgm.readings.internal;

import dev.midnightcoder.cgm.readings.IReadingQueryService;
import dev.midnightcoder.cgm.readings.dto.ReadingHistoryResult;
import dev.midnightcoder.cgm.readings.dto.ReadingView;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@Service
public class ReadingQueryService implements IReadingQueryService {
    private final ReadingRepository repository;

    ReadingQueryService(ReadingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ReadingView> findLatest(UUID sensorId) {
        return repository.findAll().stream()
            .map(this::mapToView)
            .filter(r -> r.sensorId().equals(sensorId))
            .max(Comparator.comparing(ReadingView::capturedAt));
    }

    @Override
    public ReadingHistoryResult findHistory(UUID sensorId, LocalDateTime from, LocalDateTime to, int limit) {
        List<ReadingView> filteredReadings = repository.findAll().stream()
            .map(this::mapToView)
            .filter(r -> r.sensorId().equals(sensorId))
            .filter(r -> !r.capturedAt().isBefore(from))
            .filter(r -> !r.capturedAt().isAfter(to))
            .limit(limit)
            .toList();

        return new ReadingHistoryResult(
            sensorId,
            from,
            to,
            limit,
            filteredReadings
        );
    }

    @Override
    public Optional<Double> calculateAverageOverWeek(UUID sensorId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusWeeks(1);

        return calculateAverageForPeriod(sensorId, oneWeekAgo, now);
    }

    @Override
    public Optional<Double> calculateAverageOverMonth(UUID sensorId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minusMonths(1);

        return calculateAverageForPeriod(sensorId, oneMonthAgo, now);
    }

    @Override
    public Optional<Double> calculateAverageOverThreeMonths(UUID sensorId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeMonthsAgo = now.minusMonths(3);

        return calculateAverageForPeriod(sensorId, threeMonthsAgo, now);
    }

    private Optional<Double> calculateAverageForPeriod(UUID sensorId, LocalDateTime from, LocalDateTime to) {
        List<ReadingView> readings = repository.findAll().stream()
                .map(this::mapToView)
                .filter(r -> r.sensorId().equals(sensorId))
                .filter(r -> !r.capturedAt().isBefore(from))
                .filter(r -> !r.capturedAt().isAfter(to))
                .toList();

        if (readings.isEmpty()) {
            return Optional.empty();
        }

        Double average = readings.stream()
                .mapToDouble(ReadingView::glucoseMgDl)
                .average()
                .orElse(0.0);

        return Optional.of(average);
    }

    private ReadingView mapToView(Reading reading) {
        return new ReadingView(
            reading.getId(),
            reading.getSensorId(),
            reading.getSequenceNumber(),
            reading.getCapturedAt(),
            reading.getGlucoseMgDl()
        );
    }
}
