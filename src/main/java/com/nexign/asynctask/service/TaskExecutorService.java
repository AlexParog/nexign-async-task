package com.nexign.asynctask.service;

import com.nexign.asynctask.entity.Task;
import com.nexign.asynctask.entity.TaskStatus;
import com.nexign.asynctask.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Сервис для асинхронного выполнения задач с использованием пула потоков.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskExecutorService {

    /**
     * Репозиторий для работы с сущностями задач.
     */
    private final TaskRepository taskRepository;

    /**
     * Мапа для хранения блокировок для каждой задачи.
     */
    private final Map<Long, Object> taskLocks = new ConcurrentHashMap<>();

    /**
     * Экземпляр пула потоков для асинхронного выполнения задач.
     */
    private ExecutorService executorService;

    @Value("${spring.kafka.workers.pool}")
    private int workersPool;

    /**
     * Инициализирует пул потоков с заданным размером.
     */
    @PostConstruct
    public void init() {
        log.info("Workers Pool: {}", workersPool);
        executorService = Executors.newFixedThreadPool(workersPool);
    }

    /**
     * Асинхронно выполняет задачу.
     * <p>Задача выполняется в отдельном потоке, статус задачи обновляется в процессе выполнения.</p>
     *
     * @param task задача, которую необходимо обработать
     */
    public void executeAsync(Task task) {
        Object lock = taskLocks.computeIfAbsent(task.getId(), id -> new Object());
        executorService.submit(() -> {
            synchronized (lock) {
                try {
                    log.info("Processing task ID: {}", task.getId());
                    task.setStatus(TaskStatus.IN_PROGRESS);
                    taskRepository.save(task);

                    // Симуляция работы
                    Thread.sleep(task.getDuration() * 1000L);

                    task.setStatus(TaskStatus.COMPLETED);
                    task.setResult("Task successfully completed");
                    taskRepository.save(task);

                } catch (Exception e) {
                    log.error("Task processing failed for ID: {}", task.getId(), e);
                    task.setStatus(TaskStatus.FAILED);
                    task.setResult("Task failed due to an error");
                    taskRepository.save(task);
                } finally {
                    taskLocks.remove(task.getId());
                    log.info("Task ID {} completed and lock released.", task.getId());
                }
            }
        });
    }
}


