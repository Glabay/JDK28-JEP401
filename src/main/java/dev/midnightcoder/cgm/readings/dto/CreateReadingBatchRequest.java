package dev.midnightcoder.cgm.readings.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateReadingBatchRequest(
    @NotEmpty
    @Size(max = 1000)
    List<@Valid CreateReadingRequest> readings

) {}