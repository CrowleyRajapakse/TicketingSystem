package com.csse.ticketsystem.service.mapper;

import com.csse.ticketsystem.domain.*;
import com.csse.ticketsystem.service.dto.UserExtraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserExtra and its DTO UserExtraDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserExtraMapper extends EntityMapper<UserExtraDTO, UserExtra> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserExtraDTO toDto(UserExtra userExtra); 

    @Mapping(source = "userId", target = "user")
    UserExtra toEntity(UserExtraDTO userExtraDTO);

    default UserExtra fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserExtra userExtra = new UserExtra();
        userExtra.setId(id);
        return userExtra;
    }
}
