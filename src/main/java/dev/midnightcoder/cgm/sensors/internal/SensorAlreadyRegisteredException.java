package dev.midnightcoder.cgm.sensors.internal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class SensorAlreadyRegisteredException extends RuntimeException {
    public SensorAlreadyRegisteredException(String serialNumber) {
        super("Sensor already registered: " + serialNumber);
    }
}