package dev.midnightcoder.cgm.sensors.internal;

import dev.midnightcoder.cgm.sensors.ISensorService;
import dev.midnightcoder.cgm.sensors.dto.RegisterSensorRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@RestController
@RequestMapping("/api/v1/sensors")
public class SensorControllerAPI {
    private final ISensorService sensorService;

    public SensorControllerAPI(ISensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping
    public ResponseEntity<?> registerSensor(
            @Valid @RequestBody RegisterSensorRequest request,
            BindingResult binding
    ) {
        if (binding.hasErrors())
            return ResponseEntity.badRequest().body(binding.getAllErrors());

        var result = sensorService.register(
            request.toRegistration()
        );

        IO.println(result);
        return ResponseEntity
                .created(URI.create("/api/v1/sensors/" + result.sensorId()))
                .body(result);
    }
}