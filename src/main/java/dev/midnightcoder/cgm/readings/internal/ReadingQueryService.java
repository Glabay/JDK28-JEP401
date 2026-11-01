package dev.midnightcoder.cgm.readings.internal;

import dev.midnightcoder.cgm.readings.IReadingQueryService;
import dev.midnightcoder.cgm.readings.dto.ReadingHistoryResult;
import dev.midnightcoder.cgm.readings.dto.ReadingView;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@Service
public class ReadingQueryService implements IReadingQueryService {

    @Override
    public Optional<ReadingView> findLatest(UUID sensorId) {
        // TODO: Implement actual data retrieval logic
        return Optional.empty();
    }

    @Override
    public ReadingHistoryResult findHistory(UUID sensorId, Instant from, Instant to, int limit) {
        // TODO: Implement actual data retrieval logic
        return null;
    }
}
