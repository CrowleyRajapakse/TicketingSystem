package com.csse.ticketsystem.service.mapper;

import com.csse.ticketsystem.domain.*;
import com.csse.ticketsystem.service.dto.JourneyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Journey and its DTO JourneyDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface JourneyMapper extends EntityMapper<JourneyDTO, Journey> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.vehicle_name", target = "vehicleVehicle_name")
    JourneyDTO toDto(Journey journey); 

    @Mapping(source = "vehicleId", target = "vehicle")
    Journey toEntity(JourneyDTO journeyDTO);

    default Journey fromId(Long id) {
        if (id == null) {
            return null;
        }
        Journey journey = new Journey();
        journey.setId(id);
        return journey;
    }
}
