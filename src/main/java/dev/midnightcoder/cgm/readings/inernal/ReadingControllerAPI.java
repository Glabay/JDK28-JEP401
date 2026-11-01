package dev.midnightcoder.cgm.readings.inernal;

import dev.midnightcoder.cgm.readings.dto.CreateReadingRequest;
import dev.midnightcoder.cgm.readings.IReadingIngestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@RestController
@RequestMapping("/api/readings")
public class ReadingControllerAPI {
    private final IReadingIngestionService ingestionService;

    public ReadingControllerAPI(IReadingIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/{sensorId}/")
    public ResponseEntity<?> acceptReading(
        @PathVariable UUID sensorId,
        @Valid @RequestBody CreateReadingRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                .body("Invalid request");
        }
        ingestionService.ingestReading(sensorId, List.of(request.toSubmission()));
        return ResponseEntity.ok("Reading accepted");
    }
}
