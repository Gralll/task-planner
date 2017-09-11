package com.gralll.taskplanner.service;

import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.repository.AuthorityRepository;
import com.gralll.taskplanner.repository.UserRepository;
import com.gralll.taskplanner.security.AuthoritiesConstants;
import com.gralll.taskplanner.service.dto.UserDTO;
import com.gralll.taskplanner.service.dto.UserWithPasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    public User createUser(UserWithPasswordDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setCreatedDate(LocalDateTime.now());
        //user.setCreatedDate(userDTO.getCreatedDate());

        user.setAuthorities(Collections.singleton(authorityRepository.findOne(AuthoritiesConstants.USER)));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        log.debug("User was created: {}", user);
        return user;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }
}