package dev.midnightcoder.cgm.sensors;

import dev.midnightcoder.cgm.sensors.dto.SensorRegistration;
import dev.midnightcoder.cgm.sensors.dto.SensorRegistrationResult;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
public interface ISensorService {
    SensorRegistrationResult register(SensorRegistration registration);
}