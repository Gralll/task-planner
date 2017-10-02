package com.gralll.taskplanner.service.mapper;

import com.gralll.taskplanner.domain.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper extends EntityMapper <String, Authority> {

    default Authority toEntity(String name) {
        if (name == null) {
            return null;
        }
        Authority authority = new Authority();
        authority.setName(name);
        return authority;
    }

    default String toDto(Authority authority) {
        if (authority == null) {
            return null;
        }
        return authority.getName();
    }
}

