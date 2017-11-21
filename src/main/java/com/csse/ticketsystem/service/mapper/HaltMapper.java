package com.csse.ticketsystem.service.mapper;

import com.csse.ticketsystem.domain.*;
import com.csse.ticketsystem.service.dto.HaltDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Halt and its DTO HaltDTO.
 */
@Mapper(componentModel = "spring", uses = {RouteMapper.class})
public interface HaltMapper extends EntityMapper<HaltDTO, Halt> {

    @Mapping(source = "route.id", target = "routeId")
    @Mapping(source = "route.route_name", target = "routeRoute_name")
    HaltDTO toDto(Halt halt); 

    @Mapping(source = "routeId", target = "route")
    Halt toEntity(HaltDTO haltDTO);

    default Halt fromId(Long id) {
        if (id == null) {
            return null;
        }
        Halt halt = new Halt();
        halt.setId(id);
        return halt;
    }
}
