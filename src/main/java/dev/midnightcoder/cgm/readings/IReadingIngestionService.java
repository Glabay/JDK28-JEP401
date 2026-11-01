package dev.midnightcoder.cgm.readings;

import dev.midnightcoder.cgm.readings.dto.ReadingBatchResult;
import dev.midnightcoder.cgm.readings.dto.ReadingSubmission;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
public interface IReadingIngestionService {
    ReadingBatchResult ingestReading(UUID sensorId, @Valid List<ReadingSubmission> request);
}
