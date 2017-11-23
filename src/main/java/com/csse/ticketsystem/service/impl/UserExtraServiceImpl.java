package com.csse.ticketsystem.service.impl;

import com.csse.ticketsystem.service.UserExtraService;
import com.csse.ticketsystem.domain.UserExtra;
import com.csse.ticketsystem.repository.UserExtraRepository;
import com.csse.ticketsystem.repository.search.UserExtraSearchRepository;
import com.csse.ticketsystem.service.dto.UserExtraDTO;
import com.csse.ticketsystem.service.mapper.UserExtraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserExtra.
 */
@Service
@Transactional
public class UserExtraServiceImpl implements UserExtraService{

    private final Logger log = LoggerFactory.getLogger(UserExtraServiceImpl.class);

    private final UserExtraRepository userExtraRepository;

    private final UserExtraMapper userExtraMapper;

    private final UserExtraSearchRepository userExtraSearchRepository;

    public UserExtraServiceImpl(UserExtraRepository userExtraRepository, UserExtraMapper userExtraMapper, UserExtraSearchRepository userExtraSearchRepository) {
        this.userExtraRepository = userExtraRepository;
        this.userExtraMapper = userExtraMapper;
        this.userExtraSearchRepository = userExtraSearchRepository;
    }

    /**
     * Save a userExtra.
     *
     * @param userExtraDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserExtraDTO save(UserExtraDTO userExtraDTO) {
        log.debug("Request to save UserExtra : {}", userExtraDTO);
        UserExtra userExtra = userExtraMapper.toEntity(userExtraDTO);
        userExtra = userExtraRepository.save(userExtra);
        UserExtraDTO result = userExtraMapper.toDto(userExtra);
        userExtraSearchRepository.save(userExtra);
        return result;
    }

    /**
     *  Get all the userExtras.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserExtraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserExtras");
        return userExtraRepository.findAll(pageable)
            .map(userExtraMapper::toDto);
    }

    /**
     *  Get one userExtra by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserExtraDTO findOne(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        UserExtra userExtra = userExtraRepository.findOne(id);
        return userExtraMapper.toDto(userExtra);
    }

    /**
     *  Delete the  userExtra by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserExtra : {}", id);
        userExtraRepository.delete(id);
        userExtraSearchRepository.delete(id);
    }

    /**
     * Search for the userExtra corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserExtraDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserExtras for query {}", query);
        Page<UserExtra> result = userExtraSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(userExtraMapper::toDto);
    }
}
