package com.gralll.taskplanner.service;

import com.gralll.taskplanner.domain.Task;
import com.gralll.taskplanner.repository.TaskRepository;
import com.gralll.taskplanner.service.dto.TaskDto;
import com.gralll.taskplanner.service.mapper.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

    public static final Logger LOG = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;


    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional(readOnly = true)
    public Page<TaskDto> findAllByUsername(String username, Pageable pageable) {
        LOG.debug("Request to get all Tasks from user {}.", username);
        return taskRepository.findByUserLogin(username, pageable).map(taskMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TaskDto> findAllByUserId(Long userId, Pageable pageable) {
        LOG.debug("Request to get all Tasks from user with id {}.", userId);
        return taskRepository.findByUserId(userId, pageable).map(taskMapper::toDto);
    }

    public TaskDto save(TaskDto taskDto) {
        LOG.debug("Request to create new task {}", taskDto);
        Task task = taskMapper.toEntity(taskDto);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }
}