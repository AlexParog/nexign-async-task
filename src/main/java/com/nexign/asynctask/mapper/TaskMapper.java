package com.nexign.asynctask.mapper;

import com.nexign.asynctask.dto.TaskRequestDto;
import com.nexign.asynctask.dto.TaskResponseDto;
import com.nexign.asynctask.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Маппер для преобразования между сущностью {@link Task} и DTO.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    /**
     * Преобразует сущность {@link Task} в DTO {@link TaskResponseDto}.
     *
     * @param task сущность задачи
     * @return DTO с информацией о задаче
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "taskName", source = "taskName")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "result", source = "result")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "lastModifiedAt", source = "lastModifiedAt")
    TaskResponseDto toTaskResponseDto(Task task);

    /**
     * Преобразует DTO {@link TaskRequestDto} в сущность {@link Task}.
     *
     * @param taskRequestDto DTO с данными для создания задачи
     * @return сущность задачи
     */
    Task toTask(TaskRequestDto taskRequestDto);
}
