package com.gralll.taskplanner.rest;

import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.service.UserService;
import com.gralll.taskplanner.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
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
}
