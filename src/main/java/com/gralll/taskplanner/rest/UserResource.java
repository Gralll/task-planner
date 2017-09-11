package com.gralll.taskplanner.rest;

import com.gralll.taskplanner.config.Constants;
import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.service.UserService;
import com.gralll.taskplanner.service.dto.UserDTO;
import com.gralll.taskplanner.service.dto.UserWithPasswordDTO;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody UserWithPasswordDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            return ResponseEntity.badRequest()
                    .header("error", "New user should not have id")
                    .body(null);
        } else if (userService.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                    .header("error", "Such login already exists")
                    .body(null);
        } else if (userService.findOneByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .header("error", "Such email already exists")
                    .body(null);
        } else {
            User newUser = userService.createUser(userDTO);
            return ResponseEntity.created(new URI("users/" + newUser.getLogin()))
                    .header("alert", "User was created: " + newUser.getLogin())
                    .body(newUser);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(@ApiParam Pageable pageable) {
        final Page<UserDTO> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok().header(null).body(page.getContent());
    }

    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("Request to get User : {}", login);
        return userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new).map(response -> ResponseEntity.ok().headers(null).body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
