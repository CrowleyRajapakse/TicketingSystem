package com.csse.ticketsystem.service.impl;

import com.csse.ticketsystem.service.SmartCardService;
import com.csse.ticketsystem.domain.SmartCard;
import com.csse.ticketsystem.repository.SmartCardRepository;
import com.csse.ticketsystem.repository.search.SmartCardSearchRepository;
import com.csse.ticketsystem.service.dto.SmartCardDTO;
import com.csse.ticketsystem.service.mapper.SmartCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SmartCard.
 */
@Service
@Transactional
public class SmartCardServiceImpl implements SmartCardService{

    private final Logger log = LoggerFactory.getLogger(SmartCardServiceImpl.class);

    private final SmartCardRepository smartCardRepository;

    private final SmartCardMapper smartCardMapper;

    private final SmartCardSearchRepository smartCardSearchRepository;

    public SmartCardServiceImpl(SmartCardRepository smartCardRepository, SmartCardMapper smartCardMapper, SmartCardSearchRepository smartCardSearchRepository) {
        this.smartCardRepository = smartCardRepository;
        this.smartCardMapper = smartCardMapper;
        this.smartCardSearchRepository = smartCardSearchRepository;
    }

    /**
     * Save a smartCard.
     *
     * @param smartCardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SmartCardDTO save(SmartCardDTO smartCardDTO) {
        log.debug("Request to save SmartCard : {}", smartCardDTO);
        SmartCard smartCard = smartCardMapper.toEntity(smartCardDTO);
        smartCard = smartCardRepository.save(smartCard);
        SmartCardDTO result = smartCardMapper.toDto(smartCard);
        smartCardSearchRepository.save(smartCard);
        return result;
    }

    /**
     *  Get all the smartCards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SmartCardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SmartCards");
        return smartCardRepository.findAll(pageable)
            .map(smartCardMapper::toDto);
    }

    /**
     *  Get one smartCard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SmartCardDTO findOne(Long id) {
        log.debug("Request to get SmartCard : {}", id);
        SmartCard smartCard = smartCardRepository.findOne(id);
        return smartCardMapper.toDto(smartCard);
    }

    /**
     *  Delete the  smartCard by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SmartCard : {}", id);
        smartCardRepository.delete(id);
        smartCardSearchRepository.delete(id);
    }

    /**
     * Search for the smartCard corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SmartCardDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SmartCards for query {}", query);
        Page<SmartCard> result = smartCardSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(smartCardMapper::toDto);
    }
}
