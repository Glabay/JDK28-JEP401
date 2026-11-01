package dev.midnightcoder.cgm.readings;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-31
 */
public record CreateReadingRequest(
    @Positive
    long sequenceNumber,

    @NotNull
    Instant capturedAt,

    @NotNull
    @Positive
    Integer glucoseMgDl
) {
    public ReadingSubmission toSubmission() {
        return new ReadingSubmission(
            sequenceNumber,
            capturedAt,
            glucoseMgDl
        );
    }
}
