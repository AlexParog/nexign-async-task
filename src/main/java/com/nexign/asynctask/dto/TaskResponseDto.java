package com.nexign.asynctask.dto;

import com.nexign.asynctask.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) для представления задачи в ответах.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Task response model")
public class TaskResponseDto {

    /**
     * Уникальный идентификатор задачи.
     */
    @Schema(description = "Task ID", example = "1")
    private Long id;

    /**
     * Название задачи.
     */
    @Schema(description = "Task name", example = "Reading")
    private String taskName;

    /**
     * Длительность выполнения задачи в миллисекундах.
     */
    @Schema(description = "Task duration in milliseconds", example = "1500")
    private Long duration;

    /**
     * Текущий статус задачи.
     */
    @Schema(description = "Current task status")
    private TaskStatus status;

    /**
     * Результат выполнения задачи.
     */
    @Schema(description = "Result of the task execution", example = "Task completed successfully")
    private String result;

    /**
     * Дата создания задачи.
     */
    @Schema(description = "Task creation date", example = "2024-11-26T10:15:30")
    private LocalDateTime createdAt;

    /**
     * Дата последнего изменения задачи.
     */
    @Schema(description = "Task last modification date", example = "2024-11-26T12:00:00")
    private LocalDateTime lastModifiedAt;
}
