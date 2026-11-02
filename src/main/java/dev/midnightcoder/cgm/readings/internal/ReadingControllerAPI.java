package dev.midnightcoder.cgm.readings.internal;

import dev.midnightcoder.cgm.readings.IReadingIngestionService;
import dev.midnightcoder.cgm.readings.IReadingQueryService;
import dev.midnightcoder.cgm.readings.dto.CreateReadingRequest;
import dev.midnightcoder.cgm.readings.dto.ReadingHistoryResult;
import dev.midnightcoder.cgm.readings.dto.ReadingView;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@RestController
@RequestMapping("/api/v1/readings")
public class ReadingControllerAPI {
    private final Logger log = LoggerFactory.getLogger(ReadingControllerAPI.class);
    private final IReadingIngestionService ingestionService;
    private final IReadingQueryService queryService;

    public ReadingControllerAPI(IReadingIngestionService ingestionService, IReadingQueryService queryService) {
        this.ingestionService = ingestionService;
        this.queryService = queryService;
    }

    @PostMapping("/register/{sensorId}/")
    public ResponseEntity<?> acceptReading(
        @PathVariable UUID sensorId,
        @Valid @RequestBody CreateReadingRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                .body("Invalid request\n" + bindingResult.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.joining("\n")));
        }
        var reading = ingestionService.ingestReading(sensorId, List.of(request.toSubmission()));
        log.info("Received reading for sensor {}", sensorId);
        return ResponseEntity.ok(reading);
    }

    @GetMapping("/history/{sensorId}")
    public ResponseEntity<ReadingHistoryResult> getReadingHistory(
            @PathVariable UUID sensorId,
            @RequestParam(name = "from", required = false) LocalDateTime from,
            @RequestParam(name = "to", required = false) LocalDateTime to,
            @RequestParam(name = "limit", defaultValue = "100") int limit
    ) {
        if (from == null)
            from = LocalDateTime.now()
                .minusSeconds(24 * 60 * 60);

        if (to == null)
            to = LocalDateTime .now();

        var history = queryService.findHistory(sensorId, from, to, limit);
        log.info("Reading history for sensor {} from {} to {} with limit {}", sensorId, from, to, limit);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/latest/{sensorId}")
    public ResponseEntity<ReadingView> getLatestReading(@PathVariable UUID sensorId) {
        Optional<ReadingView> latestReading = queryService.findLatest(sensorId);
        log.info("Reading latest for sensor {}", sensorId);
        return latestReading.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/average/week/{sensorId}")
    public ResponseEntity<Double> getAverageOverWeek(@PathVariable UUID sensorId) {
        Optional<Double> average = queryService.calculateAverageOverWeek(sensorId);
        log.info("Reading average over week for sensor {}", sensorId);
        return average.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/average/month/{sensorId}")
    public ResponseEntity<Double> getAverageOverMonth(@PathVariable UUID sensorId) {
        Optional<Double> average = queryService.calculateAverageOverMonth(sensorId);
        log.info("Reading average over month for sensor {}", sensorId);
        return average.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/average/a1c/{sensorId}")
    public ResponseEntity<Double> getAverageOverThreeMonths(@PathVariable UUID sensorId) {
        Optional<Double> average = queryService.calculateAverageOverThreeMonths(sensorId);
        log.info("Reading average over three months for sensor {}", sensorId);
        return average.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
