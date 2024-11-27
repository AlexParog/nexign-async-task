package com.nexign.asynctask.service.kafka;

import com.nexign.asynctask.dto.TaskRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Сервис для отправки сообщений в Kafka.
 * <p>Каждые 5 секунд генерируется и отправляется новое сообщение с задачей в топик Kafka.</p>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

    /**
     * KafkaTemplate для отправки сообщений в Kafka.
     */
    private final KafkaTemplate<String, TaskRequestDto> kafkaTemplate;

    /**
     * Максимальная длительность задачи, используемая для генерации случайных значений.
     */
    private static final long MAX_DURATION = 10;

    /**
     * Отправляет сообщение в Kafka.
     * <p>Сообщение содержит информацию о задаче с случайной длительностью.</p>
     */
    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        TaskRequestDto taskRequest = new TaskRequestDto();
        //TODO: сделать более осмысленную реализацию Message
        taskRequest.setTaskName("Task " + System.currentTimeMillis());
        taskRequest.setDuration(ThreadLocalRandom.current().nextLong(MAX_DURATION) + 1);

        kafkaTemplate.send("task-topic", taskRequest);
        log.info("Sent message: {}", taskRequest);
    }
}


