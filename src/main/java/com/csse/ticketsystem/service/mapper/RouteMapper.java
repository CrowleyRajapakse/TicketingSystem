package com.csse.ticketsystem.service.mapper;

import com.csse.ticketsystem.domain.*;
import com.csse.ticketsystem.service.dto.RouteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Route and its DTO RouteDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface RouteMapper extends EntityMapper<RouteDTO, Route> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.vehicle_name", target = "vehicleVehicle_name")
    RouteDTO toDto(Route route); 

    @Mapping(source = "vehicleId", target = "vehicle")
    Route toEntity(RouteDTO routeDTO);

    default Route fromId(Long id) {
        if (id == null) {
            return null;
        }
        Route route = new Route();
        route.setId(id);
        return route;
    }
}
