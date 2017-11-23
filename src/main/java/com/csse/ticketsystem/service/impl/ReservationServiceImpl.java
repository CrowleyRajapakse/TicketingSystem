package com.csse.ticketsystem.service.impl;

import com.csse.ticketsystem.service.ReservationService;
import com.csse.ticketsystem.domain.Reservation;
import com.csse.ticketsystem.repository.ReservationRepository;
import com.csse.ticketsystem.repository.search.ReservationSearchRepository;
import com.csse.ticketsystem.service.dto.ReservationDTO;
import com.csse.ticketsystem.service.mapper.ReservationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Reservation.
 */
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

    private final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final ReservationSearchRepository reservationSearchRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper, ReservationSearchRepository reservationSearchRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.reservationSearchRepository = reservationSearchRepository;
    }

    /**
     * Save a reservation.
     *
     * @param reservationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReservationDTO save(ReservationDTO reservationDTO) {
        log.debug("Request to save Reservation : {}", reservationDTO);
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation = reservationRepository.save(reservation);
        ReservationDTO result = reservationMapper.toDto(reservation);
        reservationSearchRepository.save(reservation);
        return result;
    }

    /**
     *  Get all the reservations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reservations");
        return reservationRepository.findAll(pageable)
            .map(reservationMapper::toDto);
    }

    /**
     *  Get one reservation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReservationDTO findOne(Long id) {
        log.debug("Request to get Reservation : {}", id);
        Reservation reservation = reservationRepository.findOne(id);
        return reservationMapper.toDto(reservation);
    }

    /**
     *  Delete the  reservation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reservation : {}", id);
        reservationRepository.delete(id);
        reservationSearchRepository.delete(id);
    }

    /**
     * Search for the reservation corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReservationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Reservations for query {}", query);
        Page<Reservation> result = reservationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(reservationMapper::toDto);
    }
}
