package com.gralll.taskplanner.rest;

import com.gralll.taskplanner.config.Constants;
import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.security.SecurityUtils;
import com.gralll.taskplanner.service.UserService;
import com.gralll.taskplanner.service.dto.UserDto;
import com.gralll.taskplanner.service.dto.UserWithPasswordDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody UserWithPasswordDto userDto) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDto);

        if (userDto.getId() != null) {
            return ResponseEntity.badRequest()
                    .header("error", "New user should not have id")
                    .body(null);
        } else if (userService.findOneByLogin(userDto.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                    .header("error", "Such login already exists")
                    .body(null);
        } else if (userService.findOneByEmail(userDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .header("error", "Such email already exists")
                    .body(null);
        } else {
            User newUser = userService.createUser(userDto);
            return ResponseEntity.created(new URI("users/" + newUser.getLogin()))
                    .header("alert", "User was created: " + newUser.getLogin())
                    .body(newUser);
        }
    }

    @PutMapping("/users")
    public ResponseEntity updateUser(@Valid @RequestBody UserWithPasswordDto userDto) throws URISyntaxException {
        log.debug("REST request to update User : {}", userDto);

        if (!SecurityUtils.isAuthenticated()) {
            throw  new AccessDeniedException("An update is only for authorized users");
        }

        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userService.findOneByLogin(userLogin);

        if (existingUser.isPresent() && (!userService.isValidPassword(userDto.getPassword(), existingUser.get().getPassword()))) {
            return ResponseEntity.badRequest().header("error", "Password is incorrect").body(null);
        }
        return userService
                .findOneByLogin(userLogin)
                .map(user -> {
                    userService.updateUser(userDto);
                    return new ResponseEntity(HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(Pageable pageable) {
        final Page<UserDto> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok().header(null).body(page.getContent());
    }

    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<UserDto> getUser(@PathVariable String login) {
        log.debug("Request to get User : {}", login);
        return userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDto::new).map(response -> ResponseEntity.ok().headers(null).body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}