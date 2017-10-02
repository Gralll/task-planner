package com.gralll.taskplanner.service.mapper;

import com.gralll.taskplanner.domain.Authority;
import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.service.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its Dto called UserDto.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper1 {

    public UserDto userToUserDto(User user) {
        return new UserDto(user);
    }

    public List<UserDto> usersToUserDtos(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDto)
            .collect(Collectors.toList());
    }

    public User userDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDto.getId());
            user.setLogin(userDto.getLogin());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            Set<Authority> authorities = this.authoritiesFromStrings(userDto.getAuthorities());
            if(authorities != null) {
                user.setAuthorities(authorities);
            }
            return user;
        }
    }

    public List<User> userDtosToUsers(List<UserDto> userDtos) {
        return userDtos.stream()
            .filter(Objects::nonNull)
            .map(this::userDtoToUser)
            .collect(Collectors.toList());
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    public Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }
}
