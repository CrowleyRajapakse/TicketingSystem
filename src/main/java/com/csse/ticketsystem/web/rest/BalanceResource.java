package com.csse.ticketsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csse.ticketsystem.service.BalanceService;
import com.csse.ticketsystem.web.rest.errors.BadRequestAlertException;
import com.csse.ticketsystem.web.rest.util.HeaderUtil;
import com.csse.ticketsystem.web.rest.util.PaginationUtil;
import com.csse.ticketsystem.service.dto.BalanceDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Balance.
 */
@RestController
@RequestMapping("/api")
public class BalanceResource {

    private final Logger log = LoggerFactory.getLogger(BalanceResource.class);

    private static final String ENTITY_NAME = "balance";

    private final BalanceService balanceService;

    public BalanceResource(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    /**
     * POST  /balances : Create a new balance.
     *
     * @param balanceDTO the balanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new balanceDTO, or with status 400 (Bad Request) if the balance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/balances")
    @Timed
    public ResponseEntity<BalanceDTO> createBalance(@Valid @RequestBody BalanceDTO balanceDTO) throws URISyntaxException {
        log.debug("REST request to save Balance : {}", balanceDTO);
        if (balanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new balance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BalanceDTO result = balanceService.save(balanceDTO);
        return ResponseEntity.created(new URI("/api/balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /balances : Updates an existing balance.
     *
     * @param balanceDTO the balanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated balanceDTO,
     * or with status 400 (Bad Request) if the balanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the balanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/balances")
    @Timed
    public ResponseEntity<BalanceDTO> updateBalance(@Valid @RequestBody BalanceDTO balanceDTO) throws URISyntaxException {
        log.debug("REST request to update Balance : {}", balanceDTO);
        if (balanceDTO.getId() == null) {
            return createBalance(balanceDTO);
        }
        BalanceDTO result = balanceService.save(balanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, balanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /balances : get all the balances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of balances in body
     */
    @GetMapping("/balances")
    @Timed
    public ResponseEntity<List<BalanceDTO>> getAllBalances(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Balances");
        Page<BalanceDTO> page = balanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/balances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /balances/:id : get the "id" balance.
     *
     * @param id the id of the balanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the balanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/balances/{id}")
    @Timed
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable Long id) {
        log.debug("REST request to get Balance : {}", id);
        BalanceDTO balanceDTO = balanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(balanceDTO));
    }

    /**
     * DELETE  /balances/:id : delete the "id" balance.
     *
     * @param id the id of the balanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/balances/{id}")
    @Timed
    public ResponseEntity<Void> deleteBalance(@PathVariable Long id) {
        log.debug("REST request to delete Balance : {}", id);
        balanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/balances?query=:query : search for the balance corresponding
     * to the query.
     *
     * @param query the query of the balance search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/balances")
    @Timed
    public ResponseEntity<List<BalanceDTO>> searchBalances(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Balances for query {}", query);
        Page<BalanceDTO> page = balanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/balances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * reload balance
     * @param currentAmount
     * @param reloadAmount
     * @return
     */
    @GetMapping("/balances/reload/{current_amount}/{reload_amount}")
    @Timed
    public double getTotal(@PathVariable double currentAmount, @PathVariable double reloadAmount){

        log.debug("reload balance");
        double total = balanceService.reload(currentAmount, reloadAmount);
        return total;
    }

}
