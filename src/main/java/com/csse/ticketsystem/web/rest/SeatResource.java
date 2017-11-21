package com.csse.ticketsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csse.ticketsystem.service.SeatService;
import com.csse.ticketsystem.web.rest.errors.BadRequestAlertException;
import com.csse.ticketsystem.web.rest.util.HeaderUtil;
import com.csse.ticketsystem.web.rest.util.PaginationUtil;
import com.csse.ticketsystem.service.dto.SeatDTO;
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
 * REST controller for managing Seat.
 */
@RestController
@RequestMapping("/api")
public class SeatResource {

    private final Logger log = LoggerFactory.getLogger(SeatResource.class);

    private static final String ENTITY_NAME = "seat";

    private final SeatService seatService;

    public SeatResource(SeatService seatService) {
        this.seatService = seatService;
    }

    /**
     * POST  /seats : Create a new seat.
     *
     * @param seatDTO the seatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seatDTO, or with status 400 (Bad Request) if the seat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seats")
    @Timed
    public ResponseEntity<SeatDTO> createSeat(@Valid @RequestBody SeatDTO seatDTO) throws URISyntaxException {
        log.debug("REST request to save Seat : {}", seatDTO);
        if (seatDTO.getId() != null) {
            throw new BadRequestAlertException("A new seat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeatDTO result = seatService.save(seatDTO);
        return ResponseEntity.created(new URI("/api/seats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seats : Updates an existing seat.
     *
     * @param seatDTO the seatDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seatDTO,
     * or with status 400 (Bad Request) if the seatDTO is not valid,
     * or with status 500 (Internal Server Error) if the seatDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seats")
    @Timed
    public ResponseEntity<SeatDTO> updateSeat(@Valid @RequestBody SeatDTO seatDTO) throws URISyntaxException {
        log.debug("REST request to update Seat : {}", seatDTO);
        if (seatDTO.getId() == null) {
            return createSeat(seatDTO);
        }
        SeatDTO result = seatService.save(seatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seatDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seats : get all the seats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of seats in body
     */
    @GetMapping("/seats")
    @Timed
    public ResponseEntity<List<SeatDTO>> getAllSeats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Seats");
        Page<SeatDTO> page = seatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seats/:id : get the "id" seat.
     *
     * @param id the id of the seatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seats/{id}")
    @Timed
    public ResponseEntity<SeatDTO> getSeat(@PathVariable Long id) {
        log.debug("REST request to get Seat : {}", id);
        SeatDTO seatDTO = seatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seatDTO));
    }

    /**
     * DELETE  /seats/:id : delete the "id" seat.
     *
     * @param id the id of the seatDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seats/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        log.debug("REST request to delete Seat : {}", id);
        seatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/seats?query=:query : search for the seat corresponding
     * to the query.
     *
     * @param query the query of the seat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/seats")
    @Timed
    public ResponseEntity<List<SeatDTO>> searchSeats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Seats for query {}", query);
        Page<SeatDTO> page = seatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/seats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
