package com.gralll.taskplanner.security;

import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowerCaseLogin = login.toLowerCase();
        Optional<User> dbUser = userRepository.findOneWithAuthoritiesByLogin(lowerCaseLogin);
        return dbUser.orElseThrow(() ->
                new UsernameNotFoundException("User " + lowerCaseLogin + " was not found in the database"));
    }
}
