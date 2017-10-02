package com.gralll.taskplanner.rest;

import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.security.CurrentUser;
import com.gralll.taskplanner.service.TaskService;
import com.gralll.taskplanner.service.dto.TaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/")
public class CurrentUserActionResource {

    public static final Logger LOG = LoggerFactory.getLogger(CurrentUserActionResource.class);

    private final TaskService taskService;

    public CurrentUserActionResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getCurrentUserTasks(@CurrentUser User user, Pageable pageable) {
        LOG.debug("REST request to get all tasks from current user: {}", user.getUsername());
        Page<TaskDto> userTasks = taskService.findAllByUsername(user.getUsername(), pageable);
        return new ResponseEntity<>(userTasks.getContent(), null, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTaskForCurrentUser(@Valid @RequestBody TaskDto taskDto,
                                                            @AuthenticationPrincipal User user) throws URISyntaxException {
        LOG.debug("REST request to create new task from current user: {}", user.getUsername());
        if (taskDto.getId() != null) {
            return ResponseEntity.badRequest().header("error","A new task cannot have an ID").body(null);
        }
        if (taskDto.getUserId() != null) {
            return ResponseEntity.badRequest().header("error","A new task cannot have an user ID").body(null);
        }
        taskDto.setUserId(user.getId());
        TaskDto result = taskService.save(taskDto);
        return ResponseEntity.created(new URI("tasks/" + result.getId())).body(result);
    }
}
