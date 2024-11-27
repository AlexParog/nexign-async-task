package com.nexign.asynctask.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Сущность, представляющая задачу в системе.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "task")
@Table(name = "task")
public class Task {

    /**
     * Уникальный идентификатор задачи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название задачи.
     */
    @NotNull
    @Size(min = 5, message = "The name must consist of at least 5 letters")
    @Column(name = "task_name", nullable = false)
    private String taskName;

    /**
     * Статус задачи.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    /**
     * Результат выполнения задачи.
     */
    @Column(name = "result")
    private String result;

    /**
     * Продолжительность выполнения задачи в секундах.
     */
    @NotNull
    @Column(name = "duration", nullable = false)
    @Digits(integer = 5, fraction = 2, message = "Incorrect input of the duration of the task execution")
    private Long duration;

    /**
     * Дата и время создания задачи.
     */
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего изменения задачи.
     */
    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    /**
     * Проверяет равенство объектов на основе идентификатора.
     * <p>Сравнение выполняется с учётом возможного использования Hibernate Proxy.</p>
     *
     * @param o объект для сравнения
     * @return {@code true}, если объекты равны; иначе {@code false}
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer()
                .getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    /**
     * Генерирует хэш-код для объекта.
     * <p>Учитывает возможное использование Hibernate Proxy.</p>
     *
     * @return хэш-код объекта
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                .hashCode()
                : getClass().hashCode();
    }
}
