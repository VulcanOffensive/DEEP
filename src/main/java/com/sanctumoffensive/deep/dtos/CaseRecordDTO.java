package com.sanctumoffensive.deep.dtos;

import jakarta.validation.constraints.NotBlank;

public record CaseRecordDTO(@NotBlank String title, String description, @NotBlank String context) { }
