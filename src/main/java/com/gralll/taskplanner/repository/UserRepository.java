package com.gralll.taskplanner.repository;

import com.gralll.taskplanner.domain.Authority;
import com.gralll.taskplanner.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneByAuthorities(Set<Authority> authorities);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    User findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    Page<User> findAllByLoginNot(Pageable pageable, String login);
}
