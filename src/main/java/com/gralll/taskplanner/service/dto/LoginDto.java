package com.gralll.taskplanner.service.dto;

import com.gralll.taskplanner.config.Constants;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDto {

    public LoginDto() {

    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = UserWithPasswordDto.PASSWORD_MIN_LENGTH, max = UserWithPasswordDto.PASSWORD_MAX_LENGTH)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, password);
    }
}