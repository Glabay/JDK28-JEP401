package dev.midnightcoder.cgm.readings.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

 interface ReadingRepository extends JpaRepository<Reading, UUID> {}