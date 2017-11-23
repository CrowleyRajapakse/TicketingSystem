package com.csse.ticketsystem.service.mapper;

import com.csse.ticketsystem.domain.*;
import com.csse.ticketsystem.service.dto.ReservationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reservation and its DTO ReservationDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class, SeatMapper.class})
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.vehicle_name", target = "vehicleVehicle_name")
    @Mapping(source = "seat.id", target = "seatId")
    @Mapping(source = "seat.seat_id", target = "seatSeat_id")
    ReservationDTO toDto(Reservation reservation); 

    @Mapping(source = "vehicleId", target = "vehicle")
    @Mapping(source = "seatId", target = "seat")
    Reservation toEntity(ReservationDTO reservationDTO);

    default Reservation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setId(id);
        return reservation;
    }
}
