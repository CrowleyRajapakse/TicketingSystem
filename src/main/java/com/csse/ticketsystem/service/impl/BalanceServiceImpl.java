package com.csse.ticketsystem.service.impl;

import com.csse.ticketsystem.service.BalanceService;
import com.csse.ticketsystem.domain.Balance;
import com.csse.ticketsystem.repository.BalanceRepository;
import com.csse.ticketsystem.repository.search.BalanceSearchRepository;
import com.csse.ticketsystem.service.dto.BalanceDTO;
import com.csse.ticketsystem.service.mapper.BalanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Balance.
 */
@Service
@Transactional
public class BalanceServiceImpl implements BalanceService{

    private final Logger log = LoggerFactory.getLogger(BalanceServiceImpl.class);

    private final BalanceRepository balanceRepository;

    private final BalanceMapper balanceMapper;

    private final BalanceSearchRepository balanceSearchRepository;

    public BalanceServiceImpl(BalanceRepository balanceRepository, BalanceMapper balanceMapper, BalanceSearchRepository balanceSearchRepository) {
        this.balanceRepository = balanceRepository;
        this.balanceMapper = balanceMapper;
        this.balanceSearchRepository = balanceSearchRepository;
    }

    /**
     * Save a balance.
     *
     * @param balanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BalanceDTO save(BalanceDTO balanceDTO) {
        log.debug("Request to save Balance : {}", balanceDTO);
        Balance balance = balanceMapper.toEntity(balanceDTO);
        balance = balanceRepository.save(balance);
        BalanceDTO result = balanceMapper.toDto(balance);
        balanceSearchRepository.save(balance);
        return result;
    }

    /**
     *  Get all the balances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BalanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Balances");
        return balanceRepository.findAll(pageable)
            .map(balanceMapper::toDto);
    }

    /**
     *  Get one balance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BalanceDTO findOne(Long id) {
        log.debug("Request to get Balance : {}", id);
        Balance balance = balanceRepository.findOne(id);
        return balanceMapper.toDto(balance);
    }

    /**
     *  Delete the  balance by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Balance : {}", id);
        balanceRepository.delete(id);
        balanceSearchRepository.delete(id);
    }

    /**
     * Search for the balance corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BalanceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Balances for query {}", query);
        Page<Balance> result = balanceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(balanceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public double reload(double currentAmount, double reloadAmount){
        log.debug("reload balance");
        return currentAmount+reloadAmount;
    }
}
