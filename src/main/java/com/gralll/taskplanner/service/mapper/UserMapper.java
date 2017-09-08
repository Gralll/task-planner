package com.gralll.taskplanner.service.mapper;

import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper <UserDTO, User> {

    /*@Mapping(target = "tasks", ignore = true)
    User toEntity(UserDTO actorDTO);
    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User actor = new User();
        actor.setId(id);
        return actor;
    }*/
}
