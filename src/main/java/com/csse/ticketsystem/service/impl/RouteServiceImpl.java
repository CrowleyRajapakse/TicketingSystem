package com.csse.ticketsystem.service.impl;

import com.csse.ticketsystem.service.RouteService;
import com.csse.ticketsystem.domain.Route;
import com.csse.ticketsystem.repository.RouteRepository;
import com.csse.ticketsystem.repository.search.RouteSearchRepository;
import com.csse.ticketsystem.service.dto.RouteDTO;
import com.csse.ticketsystem.service.mapper.RouteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Route.
 */
@Service
@Transactional
public class RouteServiceImpl implements RouteService{

    private final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);

    private final RouteRepository routeRepository;

    private final RouteMapper routeMapper;

    private final RouteSearchRepository routeSearchRepository;

    public RouteServiceImpl(RouteRepository routeRepository, RouteMapper routeMapper, RouteSearchRepository routeSearchRepository) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
        this.routeSearchRepository = routeSearchRepository;
    }

    /**
     * Save a route.
     *
     * @param routeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RouteDTO save(RouteDTO routeDTO) {
        log.debug("Request to save Route : {}", routeDTO);
        Route route = routeMapper.toEntity(routeDTO);
        route = routeRepository.save(route);
        RouteDTO result = routeMapper.toDto(route);
        routeSearchRepository.save(route);
        return result;
    }

    /**
     *  Get all the routes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RouteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Routes");
        return routeRepository.findAll(pageable)
            .map(routeMapper::toDto);
    }

    /**
     *  Get one route by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RouteDTO findOne(Long id) {
        log.debug("Request to get Route : {}", id);
        Route route = routeRepository.findOne(id);
        return routeMapper.toDto(route);
    }

    /**
     *  Delete the  route by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Route : {}", id);
        routeRepository.delete(id);
        routeSearchRepository.delete(id);
    }

    /**
     * Search for the route corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RouteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Routes for query {}", query);
        Page<Route> result = routeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(routeMapper::toDto);
    }
}
