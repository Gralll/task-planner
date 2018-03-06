package com.gralll.taskplanner.service;

import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.repository.AuthorityRepository;
import com.gralll.taskplanner.repository.UserRepository;
import com.gralll.taskplanner.security.AuthoritiesConstants;
import com.gralll.taskplanner.security.SecurityUtils;
import com.gralll.taskplanner.service.dto.UserDto;
import com.gralll.taskplanner.service.dto.UserWithPasswordDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Optional<User> findOneByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> findOneById(Long id) {
        return userRepository.findOneById(id);
    }

    public User createUser(UserWithPasswordDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCreatedDate(LocalDateTime.now());
        //user.setCreatedDate(userDto.getCreatedDate());

        user.setAuthorities(Collections.singleton(authorityRepository.findOne(AuthoritiesConstants.USER)));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        log.debug("User was created: {}", user);
        return user;
    }

    public void updateUser(UserDto userDto) {
        userRepository
                .findOneByLogin(SecurityUtils.getCurrentUserLogin())
                .ifPresent(user -> {
                    user.setLogin(userDto.getLogin());
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    user.setEmail(userDto.getEmail());
                    log.debug("Changed Information for User: {}", user);
                });
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDto::new);
    }

    @Transactional(propagation = Propagation.NEVER)
    public boolean isValidPassword(String requestPassword, String encodedPassword) {
        return passwordEncoder.matches(requestPassword, encodedPassword);
    }
}