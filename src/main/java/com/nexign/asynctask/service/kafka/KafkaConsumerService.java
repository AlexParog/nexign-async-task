package com.nexign.asynctask.service.kafka;

import com.nexign.asynctask.dto.TaskRequestDto;
import com.nexign.asynctask.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Сервис для потребления сообщений из Kafka.
 * <p>При получении сообщения из топика "task-topic", оно передается в {@link TaskService} для дальнейшей обработки.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    /**
     * Сервис задач.
     */
    private final TaskService taskService;

    /**
     * Потребляет сообщения из Kafka топика "task-topic" и вызывает сервис создания задачи.
     *
     * @param message сообщение, содержащее информацию о задаче
     */
    @KafkaListener(topics = "task-topic", groupId = "task-group")
    public void consume(TaskRequestDto message) {
        log.info("Received message: {}", message);
        taskService.createTask(message);
    }
}

