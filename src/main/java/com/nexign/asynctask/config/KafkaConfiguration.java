package com.nexign.asynctask.config;

import com.nexign.asynctask.dto.TaskRequestDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурационный класс для настройки взаимодействия с Apache Kafka.
 */
@EnableKafka
@Configuration
public class KafkaConfiguration {

    /**
     * Адрес сервера Kafka, указанный в конфигурации приложения.
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Создаёт фабрику потребителей для обработки сообщений из Kafka.
     *
     * @return объект {@link ConsumerFactory}, настроенный для работы с Kafka.
     */
    @Bean
    public ConsumerFactory<String, TaskRequestDto> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        JsonDeserializer<TaskRequestDto> deserializer = new JsonDeserializer<>(TaskRequestDto.class);
        deserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    /**
     * Создаёт фабрику контейнеров для обработки сообщений с использованием KafkaListener.
     *
     * @return объект {@link ConcurrentKafkaListenerContainerFactory}, настроенный для
     * работы с сообщениями Kafka.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TaskRequestDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TaskRequestDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    /**
     * Создаёт фабрику производителей для отправки сообщений в Kafka.
     *
     * @return объект {@link ProducerFactory}, настроенный для работы с Kafka.
     */
    @Bean
    public ProducerFactory<String, TaskRequestDto> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Создаёт шаблон для отправки сообщений в Kafka.
     *
     * @return объект {@link KafkaTemplate}, используемый для отправки сообщений
     * в топики Kafka.
     */
    @Bean
    public KafkaTemplate<String, TaskRequestDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}