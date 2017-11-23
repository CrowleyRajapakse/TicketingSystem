package com.csse.ticketsystem.service;

import com.csse.ticketsystem.service.dto.SmartCardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SmartCard.
 */
public interface SmartCardService {

    /**
     * Save a smartCard.
     *
     * @param smartCardDTO the entity to save
     * @return the persisted entity
     */
    SmartCardDTO save(SmartCardDTO smartCardDTO);

    /**
     *  Get all the smartCards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SmartCardDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" smartCard.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SmartCardDTO findOne(Long id);

    /**
     *  Delete the "id" smartCard.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the smartCard corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SmartCardDTO> search(String query, Pageable pageable);
}
