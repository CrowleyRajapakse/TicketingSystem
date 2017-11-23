package com.csse.ticketsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csse.ticketsystem.service.SmartCardService;
import com.csse.ticketsystem.web.rest.errors.BadRequestAlertException;
import com.csse.ticketsystem.web.rest.util.HeaderUtil;
import com.csse.ticketsystem.web.rest.util.PaginationUtil;
import com.csse.ticketsystem.service.dto.SmartCardDTO;
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
 * REST controller for managing SmartCard.
 */
@RestController
@RequestMapping("/api")
public class SmartCardResource {

    private final Logger log = LoggerFactory.getLogger(SmartCardResource.class);

    private static final String ENTITY_NAME = "smartCard";

    private final SmartCardService smartCardService;

    public SmartCardResource(SmartCardService smartCardService) {
        this.smartCardService = smartCardService;
    }

    /**
     * POST  /smart-cards : Create a new smartCard.
     *
     * @param smartCardDTO the smartCardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new smartCardDTO, or with status 400 (Bad Request) if the smartCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/smart-cards")
    @Timed
    public ResponseEntity<SmartCardDTO> createSmartCard(@Valid @RequestBody SmartCardDTO smartCardDTO) throws URISyntaxException {
        log.debug("REST request to save SmartCard : {}", smartCardDTO);
        if (smartCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new smartCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SmartCardDTO result = smartCardService.save(smartCardDTO);
        return ResponseEntity.created(new URI("/api/smart-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /smart-cards : Updates an existing smartCard.
     *
     * @param smartCardDTO the smartCardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated smartCardDTO,
     * or with status 400 (Bad Request) if the smartCardDTO is not valid,
     * or with status 500 (Internal Server Error) if the smartCardDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/smart-cards")
    @Timed
    public ResponseEntity<SmartCardDTO> updateSmartCard(@Valid @RequestBody SmartCardDTO smartCardDTO) throws URISyntaxException {
        log.debug("REST request to update SmartCard : {}", smartCardDTO);
        if (smartCardDTO.getId() == null) {
            return createSmartCard(smartCardDTO);
        }
        SmartCardDTO result = smartCardService.save(smartCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, smartCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smart-cards : get all the smartCards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of smartCards in body
     */
    @GetMapping("/smart-cards")
    @Timed
    public ResponseEntity<List<SmartCardDTO>> getAllSmartCards(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SmartCards");
        Page<SmartCardDTO> page = smartCardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/smart-cards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /smart-cards/:id : get the "id" smartCard.
     *
     * @param id the id of the smartCardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the smartCardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/smart-cards/{id}")
    @Timed
    public ResponseEntity<SmartCardDTO> getSmartCard(@PathVariable Long id) {
        log.debug("REST request to get SmartCard : {}", id);
        SmartCardDTO smartCardDTO = smartCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(smartCardDTO));
    }

    /**
     * DELETE  /smart-cards/:id : delete the "id" smartCard.
     *
     * @param id the id of the smartCardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/smart-cards/{id}")
    @Timed
    public ResponseEntity<Void> deleteSmartCard(@PathVariable Long id) {
        log.debug("REST request to delete SmartCard : {}", id);
        smartCardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/smart-cards?query=:query : search for the smartCard corresponding
     * to the query.
     *
     * @param query the query of the smartCard search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/smart-cards")
    @Timed
    public ResponseEntity<List<SmartCardDTO>> searchSmartCards(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of SmartCards for query {}", query);
        Page<SmartCardDTO> page = smartCardService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/smart-cards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
