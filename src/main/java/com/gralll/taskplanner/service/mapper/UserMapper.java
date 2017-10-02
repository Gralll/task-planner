package com.gralll.taskplanner.service.mapper;

import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {AuthorityMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper <UserDto, User>{

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}