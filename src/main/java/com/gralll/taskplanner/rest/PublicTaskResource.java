package com.gralll.taskplanner.rest;

import com.gralll.taskplanner.rest.error.EntityNotFoundException;
import com.gralll.taskplanner.service.TaskService;
import com.gralll.taskplanner.service.UserService;
import com.gralll.taskplanner.service.dto.TaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/tasks")
public class PublicTaskResource {

    private final static Logger LOG = LoggerFactory.getLogger(PublicTaskResource.class);

    private final TaskService taskService;

    private final UserService userService;

    public PublicTaskResource(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasksFromUser(@PathVariable Long userId, Pageable pageable) throws URISyntaxException {
        LOG.debug("REST request to get all tasks from user with id {}", userId);
        userService.findOneById(userId).orElseThrow(() -> new EntityNotFoundException("user", userId));
        List<TaskDto> userTasks = taskService.findAllByUserId(userId, pageable).getContent();
        return ResponseEntity.ok().body(userTasks);
    }

    /*@GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(@ApiParam Pageable pageable) {
        final Page<UserDto> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok().header(null).body(page.getContent());
    }*/

}