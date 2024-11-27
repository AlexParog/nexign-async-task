package com.nexign.asynctask.service;

import com.nexign.asynctask.dto.TaskRequestDto;
import com.nexign.asynctask.dto.TaskResponseDto;
import com.nexign.asynctask.entity.TaskStatus;

import java.util.List;

/**
 * Интерфейс для описания сервиса управления задачами.
 */
public interface TaskService {
    /**
     * Создаёт новую задачу на основе переданных данных.
     *
     * @param taskRequestDto данные для создания задачи
     * @return DTO с информацией о созданной задаче
     */
    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    /**
     * Возвращает статус задачи по её идентификатору.
     *
     * @param id идентификатор задачи
     * @return статус задачи
     */
    TaskStatus getTaskStatus(Long id);

    /**
     * Возвращает задачу по её идентификатору.
     *
     * @param id идентификатор задачи
     * @return DTO с информацией о задаче
     */
    TaskResponseDto getTaskById(Long id);

    /**
     * Возвращает список всех задач в системе.
     *
     * @return список DTO с информацией о задачах
     */
    List<TaskResponseDto> getAllTasks();
}
