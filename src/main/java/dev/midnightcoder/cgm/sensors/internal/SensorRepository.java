package dev.midnightcoder.cgm.sensors.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
@Repository
interface SensorRepository extends JpaRepository<Sensor, UUID> {
    boolean existsByManufacturerAndSerialNumber(String manufacturer, String serialNumber);
}