package com.csse.ticketsystem.service;

import com.csse.ticketsystem.service.dto.SeatDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Seat.
 */
public interface SeatService {

    /**
     * Save a seat.
     *
     * @param seatDTO the entity to save
     * @return the persisted entity
     */
    SeatDTO save(SeatDTO seatDTO);

    /**
     *  Get all the seats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SeatDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" seat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SeatDTO findOne(Long id);

    /**
     *  Delete the "id" seat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the seat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SeatDTO> search(String query, Pageable pageable);
}
