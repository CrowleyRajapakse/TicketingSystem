package com.csse.ticketsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csse.ticketsystem.service.HaltService;
import com.csse.ticketsystem.web.rest.errors.BadRequestAlertException;
import com.csse.ticketsystem.web.rest.util.HeaderUtil;
import com.csse.ticketsystem.web.rest.util.PaginationUtil;
import com.csse.ticketsystem.service.dto.HaltDTO;
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
 * REST controller for managing Halt.
 */
@RestController
@RequestMapping("/api")
public class HaltResource {

    private final Logger log = LoggerFactory.getLogger(HaltResource.class);

    private static final String ENTITY_NAME = "halt";

    private final HaltService haltService;

    public HaltResource(HaltService haltService) {
        this.haltService = haltService;
    }

    /**
     * POST  /halts : Create a new halt.
     *
     * @param haltDTO the haltDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new haltDTO, or with status 400 (Bad Request) if the halt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/halts")
    @Timed
    public ResponseEntity<HaltDTO> createHalt(@Valid @RequestBody HaltDTO haltDTO) throws URISyntaxException {
        log.debug("REST request to save Halt : {}", haltDTO);
        if (haltDTO.getId() != null) {
            throw new BadRequestAlertException("A new halt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HaltDTO result = haltService.save(haltDTO);
        return ResponseEntity.created(new URI("/api/halts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /halts : Updates an existing halt.
     *
     * @param haltDTO the haltDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated haltDTO,
     * or with status 400 (Bad Request) if the haltDTO is not valid,
     * or with status 500 (Internal Server Error) if the haltDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/halts")
    @Timed
    public ResponseEntity<HaltDTO> updateHalt(@Valid @RequestBody HaltDTO haltDTO) throws URISyntaxException {
        log.debug("REST request to update Halt : {}", haltDTO);
        if (haltDTO.getId() == null) {
            return createHalt(haltDTO);
        }
        HaltDTO result = haltService.save(haltDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, haltDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /halts : get all the halts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of halts in body
     */
    @GetMapping("/halts")
    @Timed
    public ResponseEntity<List<HaltDTO>> getAllHalts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Halts");
        Page<HaltDTO> page = haltService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/halts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /halts/:id : get the "id" halt.
     *
     * @param id the id of the haltDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the haltDTO, or with status 404 (Not Found)
     */
    @GetMapping("/halts/{id}")
    @Timed
    public ResponseEntity<HaltDTO> getHalt(@PathVariable Long id) {
        log.debug("REST request to get Halt : {}", id);
        HaltDTO haltDTO = haltService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(haltDTO));
    }

    /**
     * DELETE  /halts/:id : delete the "id" halt.
     *
     * @param id the id of the haltDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/halts/{id}")
    @Timed
    public ResponseEntity<Void> deleteHalt(@PathVariable Long id) {
        log.debug("REST request to delete Halt : {}", id);
        haltService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/halts?query=:query : search for the halt corresponding
     * to the query.
     *
     * @param query the query of the halt search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/halts")
    @Timed
    public ResponseEntity<List<HaltDTO>> searchHalts(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Halts for query {}", query);
        Page<HaltDTO> page = haltService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/halts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
