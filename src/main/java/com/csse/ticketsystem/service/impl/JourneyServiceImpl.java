package com.csse.ticketsystem.service.impl;

import com.csse.ticketsystem.service.JourneyService;
import com.csse.ticketsystem.domain.Journey;
import com.csse.ticketsystem.repository.JourneyRepository;
import com.csse.ticketsystem.repository.search.JourneySearchRepository;
import com.csse.ticketsystem.service.dto.JourneyDTO;
import com.csse.ticketsystem.service.mapper.JourneyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Journey.
 */
@Service
@Transactional
public class JourneyServiceImpl implements JourneyService{

    private final Logger log = LoggerFactory.getLogger(JourneyServiceImpl.class);

    private final JourneyRepository journeyRepository;

    private final JourneyMapper journeyMapper;

    private final JourneySearchRepository journeySearchRepository;

    public JourneyServiceImpl(JourneyRepository journeyRepository, JourneyMapper journeyMapper, JourneySearchRepository journeySearchRepository) {
        this.journeyRepository = journeyRepository;
        this.journeyMapper = journeyMapper;
        this.journeySearchRepository = journeySearchRepository;
    }

    /**
     * Save a journey.
     *
     * @param journeyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JourneyDTO save(JourneyDTO journeyDTO) {
        log.debug("Request to save Journey : {}", journeyDTO);
        Journey journey = journeyMapper.toEntity(journeyDTO);
        journey = journeyRepository.save(journey);
        JourneyDTO result = journeyMapper.toDto(journey);
        journeySearchRepository.save(journey);
        return result;
    }

    /**
     *  Get all the journeys.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JourneyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Journeys");
        return journeyRepository.findAll(pageable)
            .map(journeyMapper::toDto);
    }

    /**
     *  Get one journey by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JourneyDTO findOne(Long id) {
        log.debug("Request to get Journey : {}", id);
        Journey journey = journeyRepository.findOne(id);
        return journeyMapper.toDto(journey);
    }

    /**
     *  Delete the  journey by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Journey : {}", id);
        journeyRepository.delete(id);
        journeySearchRepository.delete(id);
    }

    /**
     * Search for the journey corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JourneyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Journeys for query {}", query);
        Page<Journey> result = journeySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(journeyMapper::toDto);
    }
}
