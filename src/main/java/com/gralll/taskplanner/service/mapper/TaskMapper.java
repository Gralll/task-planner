package com.gralll.taskplanner.service.mapper;

import com.gralll.taskplanner.domain.Task;
import com.gralll.taskplanner.service.dto.TaskDTO;

//@Mapper(componentModel = "spring", uses = {})
public interface TaskMapper extends EntityMapper <TaskDTO, Task> {

}
