package dev.midnightcoder.cgm.sensors.internal;

import dev.midnightcoder.cgm.sensors.ISensorService;
import dev.midnightcoder.cgm.sensors.dto.SensorRegistered;
import dev.midnightcoder.cgm.sensors.dto.SensorRegistration;
import dev.midnightcoder.cgm.sensors.dto.SensorRegistrationResult;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@Service
class SensorService implements ISensorService {
    private final SensorRepository sensorRepository;
    private final ApplicationEventPublisher eventPublisher;

    SensorService(SensorRepository sensorRepository, ApplicationEventPublisher eventPublisher) {
        this.sensorRepository = sensorRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public SensorRegistrationResult register(SensorRegistration registration) {
        if (sensorRepository.existsByManufacturerAndSerialNumber(registration.manufacturer(), registration.serialNumber()))
            throw new SensorAlreadyRegisteredException(registration.serialNumber());

        var sensor = Sensor.register(
            registration.serialNumber(),
            registration.manufacturer(),
            registration.model(),
            registration.expiresAt()
        );

        sensor = sensorRepository.saveAndFlush(sensor);

        eventPublisher.publishEvent(new SensorRegistered(
            sensor.getId(),
            sensor.getSerialNumber(),
            sensor.getRegisteredAt())
        );

        return new SensorRegistrationResult(
            sensor.getId(),
            sensor.getStatus().name(),
            sensor.getRegisteredAt()
        );
    }
}