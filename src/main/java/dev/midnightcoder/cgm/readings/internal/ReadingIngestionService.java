package dev.midnightcoder.cgm.readings.internal;

import dev.midnightcoder.cgm.readings.IReadingIngestionService;
import dev.midnightcoder.cgm.readings.dto.ReadingBatchResult;
import dev.midnightcoder.cgm.readings.dto.ReadingSubmission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    private final ReadingRepository repository;

    ReadingIngestionService(ReadingRepository repository) {this.repository = repository;}

    @Override
    @Transactional
    public ReadingBatchResult ingestReading(UUID sensorId, List<ReadingSubmission> request) {
        var batchId = UUID.randomUUID();
        var acceptedCount = request.size();
        var duplicateCount = 0;
        LocalDateTime acceptedAt = LocalDateTime.now();

        for(var submission : request) {
            var reading = new Reading();
                reading.setSensorId(sensorId);
                reading.setGlucoseMgDl(submission.glucoseMgDl());
                reading.setCapturedAt(submission.capturedAt());
                reading.setAcceptedAt(acceptedAt);
            repository.save(reading);
        }

        return new ReadingBatchResult(batchId, acceptedCount, duplicateCount, acceptedAt);
    }
}
