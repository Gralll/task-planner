package com.gralll.taskplanner.rest;

import com.gralll.taskplanner.service.TaskService;
import com.gralll.taskplanner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PublicTaskResource {

    private final Logger log = LoggerFactory.getLogger(PublicTaskResource.class);

    private final TaskService taskService;

    private final UserService userService;

    public PublicTaskResource(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }
/*
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasksFromUser(String login, Pageable pageable) throws URISyntaxException {
        return userService.findOneByLogin(login)
                .map(existedUser ->
                        ResponseEntity.ok().body(taskService.findAllByUserId(existedUser.getId(), pageable).getContent()))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(@ApiParam Pageable pageable) {
        final Page<UserDto> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok().header(null).body(page.getContent());
    }*/

}