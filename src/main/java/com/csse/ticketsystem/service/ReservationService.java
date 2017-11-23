package com.csse.ticketsystem.service;

import com.csse.ticketsystem.service.dto.ReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Reservation.
 */
public interface ReservationService {

    /**
     * Save a reservation.
     *
     * @param reservationDTO the entity to save
     * @return the persisted entity
     */
    ReservationDTO save(ReservationDTO reservationDTO);

    /**
     *  Get all the reservations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReservationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" reservation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReservationDTO findOne(Long id);

    /**
     *  Delete the "id" reservation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the reservation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReservationDTO> search(String query, Pageable pageable);
}
