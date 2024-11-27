package com.nexign.asynctask.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * Перечисление, представляющее возможные статусы задачи.
 */
@Getter
public enum TaskStatus {

    /**
     * Статус задачи, указывающий, что она создана, но ещё не начата.
     */
    CREATED("created"),

    /**
     * Статус задачи, указывающий, что она в процессе выполнения.
     */
    IN_PROGRESS("inProgress"),

    /**
     * Статус задачи, указывающий, что она успешно завершена.
     */
    COMPLETED("completed"),

    /**
     * Статус задачи, указывающий, что её выполнение завершилось с ошибкой.
     */
    FAILED("failed");

    /**
     * Строковое значение статуса.
     */
    @JsonValue
    private final String value;

    /**
     * Конструктор для создания статуса с указанным строковым значением.
     *
     * @param value строковое представление статуса
     */
    TaskStatus(String value) {
        this.value = value;
    }

    /**
     * Преобразует строковое значение в соответствующий статус.
     *
     * @param value строковое представление статуса
     * @return объект {@link TaskStatus}, соответствующий строковому значению,
     * или {@code null}, если значение некорректно
     */
    @JsonCreator
    public TaskStatus fromString(String value) {
        return Arrays.stream(values())
                .filter(status -> status.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    /**
     * Возвращает строковое представление статуса.
     *
     * @return строковое значение статуса
     */
    @Override
    public String toString() {
        return value;
    }
}
