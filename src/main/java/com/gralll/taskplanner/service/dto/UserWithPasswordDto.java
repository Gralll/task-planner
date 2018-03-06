package com.gralll.taskplanner.service.dto;

import com.gralll.taskplanner.domain.Authority;
import com.gralll.taskplanner.domain.User;

import javax.validation.constraints.Size;
import java.util.stream.Collectors;

public class UserWithPasswordDto extends UserDto {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public UserWithPasswordDto() {
    }

    public UserWithPasswordDto(User user) {
        super(user.getId(), user.getLogin(), user.getFirstName(),
                user.getLastName(), user.getEmail(), user.getCreatedDate(),
                user.getAuthorities().stream().map(Authority::getName)
                        .collect(Collectors.toSet()));

        this.password = user.getPassword();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
