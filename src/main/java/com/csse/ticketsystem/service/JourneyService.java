package com.csse.ticketsystem.service;

import com.csse.ticketsystem.service.dto.JourneyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Journey.
 */
public interface JourneyService {

    /**
     * Save a journey.
     *
     * @param journeyDTO the entity to save
     * @return the persisted entity
     */
    JourneyDTO save(JourneyDTO journeyDTO);

    /**
     *  Get all the journeys.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JourneyDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" journey.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JourneyDTO findOne(Long id);

    /**
     *  Delete the "id" journey.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the journey corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JourneyDTO> search(String query, Pageable pageable);
}
