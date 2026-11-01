package dev.midnightcoder.cgm.readings.inernal;

import dev.midnightcoder.cgm.readings.IReadingIngestionService;
import dev.midnightcoder.cgm.readings.dto.ReadingBatchResult;
import dev.midnightcoder.cgm.readings.dto.ReadingSubmission;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@Service
public class ReadingIngestionService implements IReadingIngestionService {

    @Override
    public ReadingBatchResult ingestReading(UUID sensorId, List<ReadingSubmission> request) {
        return null;
    }
}
