package com.csse.ticketsystem.service;

import com.csse.ticketsystem.service.dto.BalanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Balance.
 */
public interface BalanceService {

    /**
     * Save a balance.
     *
     * @param balanceDTO the entity to save
     * @return the persisted entity
     */
    BalanceDTO save(BalanceDTO balanceDTO);

    /**
     *  Get all the balances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BalanceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" balance.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BalanceDTO findOne(Long id);

    /**
     *  Delete the "id" balance.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the balance corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BalanceDTO> search(String query, Pageable pageable);

    /**
     * reload balance
     * @param currentAmount
     * @param reloadAmount
     * @return
     */
    double reload(double currentAmount, double reloadAmount);
}
