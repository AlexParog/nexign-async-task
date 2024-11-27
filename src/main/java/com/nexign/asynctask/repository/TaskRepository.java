package com.nexign.asynctask.repository;

import com.nexign.asynctask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Task}.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
