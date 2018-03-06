package com.gralll.taskplanner.rest;

import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.security.CurrentUser;
import com.gralll.taskplanner.service.TaskService;
import com.gralll.taskplanner.service.dto.TaskDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "Current user tasks", description = "Expose API for working with current user tasks")
@RestController
@RequestMapping( value = "/tasks",
        produces = APPLICATION_JSON_VALUE)
public class CurrentUserActionResource {

    private final Logger log = LoggerFactory.getLogger(CurrentUserActionResource.class);

    private final TaskService taskService;

    public CurrentUserActionResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "View a list of available current user tasks")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<List<TaskDto>> getCurrentUserTasks(@ApiIgnore @CurrentUser User user, Pageable pageable) {
        log.debug("REST request to get all tasks from current user: {}", user.getUsername());
        Page<TaskDto> userTasks = taskService.findAllByUsername(user.getUsername(), pageable);
        return new ResponseEntity<>(userTasks.getContent(), null, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a task for the current user")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<TaskDto> createTaskForCurrentUser(
            @Valid @RequestBody TaskDto taskDto,
            @ApiIgnore @CurrentUser User user) throws URISyntaxException {
        log.debug("REST request to create new task from current user: {}", user.getUsername());
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
