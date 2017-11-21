package com.csse.ticketsystem.service.impl;

import com.csse.ticketsystem.service.HaltService;
import com.csse.ticketsystem.domain.Halt;
import com.csse.ticketsystem.repository.HaltRepository;
import com.csse.ticketsystem.repository.search.HaltSearchRepository;
import com.csse.ticketsystem.service.dto.HaltDTO;
import com.csse.ticketsystem.service.mapper.HaltMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Halt.
 */
@Service
@Transactional
public class HaltServiceImpl implements HaltService{

    private final Logger log = LoggerFactory.getLogger(HaltServiceImpl.class);

    private final HaltRepository haltRepository;

    private final HaltMapper haltMapper;

    private final HaltSearchRepository haltSearchRepository;

    public HaltServiceImpl(HaltRepository haltRepository, HaltMapper haltMapper, HaltSearchRepository haltSearchRepository) {
        this.haltRepository = haltRepository;
        this.haltMapper = haltMapper;
        this.haltSearchRepository = haltSearchRepository;
    }

    /**
     * Save a halt.
     *
     * @param haltDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HaltDTO save(HaltDTO haltDTO) {
        log.debug("Request to save Halt : {}", haltDTO);
        Halt halt = haltMapper.toEntity(haltDTO);
        halt = haltRepository.save(halt);
        HaltDTO result = haltMapper.toDto(halt);
        haltSearchRepository.save(halt);
        return result;
    }

    /**
     *  Get all the halts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HaltDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Halts");
        return haltRepository.findAll(pageable)
            .map(haltMapper::toDto);
    }

    /**
     *  Get one halt by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HaltDTO findOne(Long id) {
        log.debug("Request to get Halt : {}", id);
        Halt halt = haltRepository.findOne(id);
        return haltMapper.toDto(halt);
    }

    /**
     *  Delete the  halt by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Halt : {}", id);
        haltRepository.delete(id);
        haltSearchRepository.delete(id);
    }

    /**
     * Search for the halt corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HaltDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Halts for query {}", query);
        Page<Halt> result = haltSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(haltMapper::toDto);
    }
}
