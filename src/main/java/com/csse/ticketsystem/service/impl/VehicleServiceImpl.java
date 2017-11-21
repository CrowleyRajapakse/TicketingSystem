package com.csse.ticketsystem.service.impl;

import com.csse.ticketsystem.service.VehicleService;
import com.csse.ticketsystem.domain.Vehicle;
import com.csse.ticketsystem.repository.VehicleRepository;
import com.csse.ticketsystem.repository.search.VehicleSearchRepository;
import com.csse.ticketsystem.service.dto.VehicleDTO;
import com.csse.ticketsystem.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Vehicle.
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService{

    private final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    private final VehicleSearchRepository vehicleSearchRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper, VehicleSearchRepository vehicleSearchRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.vehicleSearchRepository = vehicleSearchRepository;
    }

    /**
     * Save a vehicle.
     *
     * @param vehicleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehicleDTO save(VehicleDTO vehicleDTO) {
        log.debug("Request to save Vehicle : {}", vehicleDTO);
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle = vehicleRepository.save(vehicle);
        VehicleDTO result = vehicleMapper.toDto(vehicle);
        vehicleSearchRepository.save(vehicle);
        return result;
    }

    /**
     *  Get all the vehicles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehicleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vehicles");
        return vehicleRepository.findAll(pageable)
            .map(vehicleMapper::toDto);
    }


    /**
     *  get all the vehicles where Driver is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<VehicleDTO> findAllWhereDriverIsNull() {
        log.debug("Request to get all vehicles where Driver is null");
        return StreamSupport
            .stream(vehicleRepository.findAll().spliterator(), false)
            .filter(vehicle -> vehicle.getDriver() == null)
            .map(vehicleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one vehicle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleDTO findOne(Long id) {
        log.debug("Request to get Vehicle : {}", id);
        Vehicle vehicle = vehicleRepository.findOne(id);
        return vehicleMapper.toDto(vehicle);
    }

    /**
     *  Delete the  vehicle by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vehicle : {}", id);
        vehicleRepository.delete(id);
        vehicleSearchRepository.delete(id);
    }

    /**
     * Search for the vehicle corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehicleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Vehicles for query {}", query);
        Page<Vehicle> result = vehicleSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(vehicleMapper::toDto);
    }
}
