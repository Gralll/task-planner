package com.gralll.taskplanner.service.mapper;

import com.gralll.taskplanner.domain.Task;
import com.gralll.taskplanner.service.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TaskMapper extends EntityMapper <TaskDto, Task> {

    @Mapping(source = "user.id", target = "userId")
    TaskDto toDto(Task task);

    @Mapping(source = "userId", target = "user")
    Task toEntity(TaskDto taskDto);

    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}

