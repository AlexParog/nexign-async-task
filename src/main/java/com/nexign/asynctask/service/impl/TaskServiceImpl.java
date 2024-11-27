package com.nexign.asynctask.service.impl;

import com.nexign.asynctask.dto.TaskRequestDto;
import com.nexign.asynctask.dto.TaskResponseDto;
import com.nexign.asynctask.entity.Task;
import com.nexign.asynctask.entity.TaskStatus;
import com.nexign.asynctask.exception.NotFoundException;
import com.nexign.asynctask.exception.ValidationTaskException;
import com.nexign.asynctask.mapper.TaskMapper;
import com.nexign.asynctask.repository.TaskRepository;
import com.nexign.asynctask.service.TaskExecutorService;
import com.nexign.asynctask.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация сервиса управления задачами.
 * <p>Предоставляет методы для создания, получения, проверки статуса и обработки задач.</p>
 */
@Service
public class TaskServiceImpl implements TaskService {
    /**
     * Репозиторий задач.
     */
    private final TaskRepository taskRepository;
    /**
     * Маппер задач.
     */
    private final TaskMapper taskMapper;
    /**
     * Сервис асинхронной обработки задач.
     */
    private final TaskExecutorService taskExecutorService;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, TaskExecutorService taskExecutorService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskExecutorService = taskExecutorService;
    }

    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        if (taskRequestDto.getTaskName() == null || taskRequestDto.getTaskName().isBlank()) {
            throw new ValidationTaskException("Task name cannot be empty");
        }

        if (taskRequestDto.getDuration() == null || taskRequestDto.getDuration() <= 0) {
            throw new ValidationTaskException("Task duration must be grater than 0");
        }
        Task task = taskMapper.toTask(taskRequestDto);
        task.setStatus(TaskStatus.CREATED);
        taskRepository.save(task);

        // асинхронно обрабатываем задачу
        taskExecutorService.executeAsync(task);

        task = taskRepository.save(task);

        return taskMapper.toTaskResponseDto(task);
    }

    @Override
    public TaskStatus getTaskStatus(Long id) {
        validateId(id);
        Task task = findTaskOrNotFound(id);
        return task.getStatus();
    }

    @Override
    public TaskResponseDto getTaskById(Long id) {
        validateId(id);
        Task task = findTaskOrNotFound(id);
        return taskMapper.toTaskResponseDto(task);
    }

    @Override
    public List<TaskResponseDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            throw new NotFoundException("No tasks found in the system");
        }

        return tasks.stream()
                .map(taskMapper::toTaskResponseDto)
                .toList();
    }

    /**
     * Находит задачу по идентификатору или выбрасывает исключение, если она не найдена.
     *
     * @param id идентификатор задачи
     * @return сущность задачи
     * @throws NotFoundException если задача не найдена
     */
    private Task findTaskOrNotFound(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Задача c id={0} не найдена", id));
    }

    /**
     * Валидирует идентификатор задачи.
     *
     * @param id идентификатор задачи
     * @throws ValidationTaskException если идентификатор равен null или меньше 1
     */
    private void validateId(Long id) {
        if (id == null) {
            throw new ValidationTaskException("Task ID cannot be null");
        }
        if (id <= 0) {
            throw new ValidationTaskException("Task ID must be greater than 0");
        }
    }
}
