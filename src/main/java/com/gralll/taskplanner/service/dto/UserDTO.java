package com.gralll.taskplanner.service.dto;

import com.gralll.taskplanner.config.Constants;
import com.gralll.taskplanner.domain.Authority;
import com.gralll.taskplanner.domain.User;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 100)
    private String login;

    /*@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;*/

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private LocalDateTime createdDate;

    private Set<String> authorities;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getId(), user.getLogin(), user.getFirstName(),
                user.getLastName(), user.getEmail(), user.getCreatedDate(),
                user.getAuthorities().stream().map(Authority::getName)
                        .collect(Collectors.toSet()));
    }

    public UserDTO(Long id, String login, String firstName, String lastName, String email, LocalDateTime createdDate, Set<String> authorities) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdDate = createdDate;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

   /* public String getPassword() {
        return password;
    }*/

    /*public void setPassword(String password) {
        this.password = password;
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this);
    }
}