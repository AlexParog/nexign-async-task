package com.nexign.asynctask.controller;

import com.nexign.asynctask.dto.TaskRequestDto;
import com.nexign.asynctask.dto.TaskResponseDto;
import com.nexign.asynctask.entity.TaskStatus;
import com.nexign.asynctask.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-Контроллер для работы с задачами.
 */
@RestController
@RequestMapping("/api/task")
public class TaskController {

    /**
     * Сервис задач.
     */
    private final TaskService taskService;

    /**
     * Конструктор контроллера.
     *
     * @param taskService Сервис для работы с задачами.
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Создание новой задачи.
     *
     * @param taskRequestDto Данные для создания задачи.
     * @return DTO созданной задачи.
     */
    @Operation(summary = "Создание задачи", description = "Создает новую задачу и отправляет в Kafka для асинхронной работы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача успешно создана",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    })
    @PostMapping
    public TaskResponseDto createTask(@RequestBody @Valid TaskRequestDto taskRequestDto) {
        return taskService.createTask(taskRequestDto);
    }

    /**
     * Получение задачи по ID.
     *
     * @param id ID задачи.
     * @return Ответ с информацией о задаче.
     */
    @Operation(summary = "Получение задачи по ID", description = "Возвращает информацию о задаче по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    /**
     * Получение актуального статуса задачи по ID.
     *
     * @param id ID задачи.
     * @return Ответ с актуальным статусом задачи.
     */
    @Operation(summary = "Получение статуса задачи по ID", description = "Возвращает актуальный статус задачи по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Актуальный статус задачи",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskStatus.class))),
            @ApiResponse(responseCode = "404", description = "Задача не найдена",
                    content = @Content)
    })
    @GetMapping("/{id}/status")
    public ResponseEntity<TaskStatus> getTaskStatusById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskStatus(id), HttpStatus.OK);
    }

    /**
     * Получение всех задач.
     *
     * @return Список всех задач.
     */
    @Operation(summary = "Получение всех задач", description = "Возвращает список всех задач")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<TaskResponseDto> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }
}
