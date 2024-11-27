package com.nexign.asynctask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) для создания задачи.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Task request model")
public class TaskRequestDto {

    /**
     * Название задачи.
     */
    @NotBlank(message = "Task name is required")
    @Schema(description = "Task name", example = "Reading")
    private String taskName;

    /**
     * Длительность выполнения задачи в миллисекундах.
     */
    @NotNull(message = "Task duration is required")
    @Positive(message = "Duration must be positive")
    @Schema(description = "Task duration in milliseconds", example = "1500")
    private Long duration;
}
