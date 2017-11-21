package com.csse.ticketsystem.service.impl;

import com.csse.ticketsystem.service.SeatService;
import com.csse.ticketsystem.domain.Seat;
import com.csse.ticketsystem.repository.SeatRepository;
import com.csse.ticketsystem.repository.search.SeatSearchRepository;
import com.csse.ticketsystem.service.dto.SeatDTO;
import com.csse.ticketsystem.service.mapper.SeatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Seat.
 */
@Service
@Transactional
public class SeatServiceImpl implements SeatService{

    private final Logger log = LoggerFactory.getLogger(SeatServiceImpl.class);

    private final SeatRepository seatRepository;

    private final SeatMapper seatMapper;

    private final SeatSearchRepository seatSearchRepository;

    public SeatServiceImpl(SeatRepository seatRepository, SeatMapper seatMapper, SeatSearchRepository seatSearchRepository) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
        this.seatSearchRepository = seatSearchRepository;
    }

    /**
     * Save a seat.
     *
     * @param seatDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeatDTO save(SeatDTO seatDTO) {
        log.debug("Request to save Seat : {}", seatDTO);
        Seat seat = seatMapper.toEntity(seatDTO);
        seat = seatRepository.save(seat);
        SeatDTO result = seatMapper.toDto(seat);
        seatSearchRepository.save(seat);
        return result;
    }

    /**
     *  Get all the seats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Seats");
        return seatRepository.findAll(pageable)
            .map(seatMapper::toDto);
    }

    /**
     *  Get one seat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeatDTO findOne(Long id) {
        log.debug("Request to get Seat : {}", id);
        Seat seat = seatRepository.findOne(id);
        return seatMapper.toDto(seat);
    }

    /**
     *  Delete the  seat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seat : {}", id);
        seatRepository.delete(id);
        seatSearchRepository.delete(id);
    }

    /**
     * Search for the seat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeatDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Seats for query {}", query);
        Page<Seat> result = seatSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(seatMapper::toDto);
    }
}
