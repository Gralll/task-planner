package com.gralll.taskplanner.service.mapper;

import com.gralll.taskplanner.domain.Task;
import com.gralll.taskplanner.service.dto.TaskDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TaskMapper extends EntityMapper <TaskDTO, Task> {

    /*@Mapping(target = )
    Task toEntity(TaskDTO taskDTO);
    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }*/
}
