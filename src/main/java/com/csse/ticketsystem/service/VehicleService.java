package com.csse.ticketsystem.service;

import com.csse.ticketsystem.service.dto.VehicleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Vehicle.
 */
public interface VehicleService {

    /**
     * Save a vehicle.
     *
     * @param vehicleDTO the entity to save
     * @return the persisted entity
     */
    VehicleDTO save(VehicleDTO vehicleDTO);

    /**
     *  Get all the vehicles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VehicleDTO> findAll(Pageable pageable);
    /**
     *  Get all the VehicleDTO where Driver is null.
     *
     *  @return the list of entities
     */
    List<VehicleDTO> findAllWhereDriverIsNull();

    /**
     *  Get the "id" vehicle.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VehicleDTO findOne(Long id);

    /**
     *  Delete the "id" vehicle.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the vehicle corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VehicleDTO> search(String query, Pageable pageable);
}
