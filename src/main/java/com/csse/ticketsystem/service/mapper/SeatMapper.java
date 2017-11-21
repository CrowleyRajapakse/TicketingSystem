package com.csse.ticketsystem.service.mapper;

import com.csse.ticketsystem.domain.*;
import com.csse.ticketsystem.service.dto.SeatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Seat and its DTO SeatDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface SeatMapper extends EntityMapper<SeatDTO, Seat> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.vehicle_name", target = "vehicleVehicle_name")
    SeatDTO toDto(Seat seat); 

    @Mapping(source = "vehicleId", target = "vehicle")
    Seat toEntity(SeatDTO seatDTO);

    default Seat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seat seat = new Seat();
        seat.setId(id);
        return seat;
    }
}
