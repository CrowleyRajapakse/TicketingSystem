package com.csse.ticketsystem.service;

import com.csse.ticketsystem.service.dto.HaltDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Halt.
 */
public interface HaltService {

    /**
     * Save a halt.
     *
     * @param haltDTO the entity to save
     * @return the persisted entity
     */
    HaltDTO save(HaltDTO haltDTO);

    /**
     *  Get all the halts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HaltDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" halt.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HaltDTO findOne(Long id);

    /**
     *  Delete the "id" halt.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the halt corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HaltDTO> search(String query, Pageable pageable);
}
