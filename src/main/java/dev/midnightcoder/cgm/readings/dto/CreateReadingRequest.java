package dev.midnightcoder.cgm.readings.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
public value record CreateReadingRequest(
    @Positive
    Long sequenceNumber,

    @NotNull
    LocalDateTime capturedAt,

    @NotNull
    @Positive
    Double glucoseMgDl
) {
    public ReadingSubmission toSubmission() {
        return new ReadingSubmission(
            sequenceNumber,
            capturedAt,
            glucoseMgDl
        );
    }
}
