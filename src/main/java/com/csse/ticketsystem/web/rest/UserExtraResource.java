package com.csse.ticketsystem.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.csse.ticketsystem.service.UserExtraService;
import com.csse.ticketsystem.web.rest.errors.BadRequestAlertException;
import com.csse.ticketsystem.web.rest.util.HeaderUtil;
import com.csse.ticketsystem.web.rest.util.PaginationUtil;
import com.csse.ticketsystem.service.dto.UserExtraDTO;
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
 * REST controller for managing UserExtra.
 */
@RestController
@RequestMapping("/api")
public class UserExtraResource {

    private final Logger log = LoggerFactory.getLogger(UserExtraResource.class);

    private static final String ENTITY_NAME = "userExtra";

    private final UserExtraService userExtraService;

    public UserExtraResource(UserExtraService userExtraService) {
        this.userExtraService = userExtraService;
    }

    /**
     * POST  /user-extras : Create a new userExtra.
     *
     * @param userExtraDTO the userExtraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExtraDTO, or with status 400 (Bad Request) if the userExtra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-extras")
    @Timed
    public ResponseEntity<UserExtraDTO> createUserExtra(@Valid @RequestBody UserExtraDTO userExtraDTO) throws URISyntaxException {
        log.debug("REST request to save UserExtra : {}", userExtraDTO);
        if (userExtraDTO.getId() != null) {
            throw new BadRequestAlertException("A new userExtra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserExtraDTO result = userExtraService.save(userExtraDTO);
        return ResponseEntity.created(new URI("/api/user-extras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-extras : Updates an existing userExtra.
     *
     * @param userExtraDTO the userExtraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExtraDTO,
     * or with status 400 (Bad Request) if the userExtraDTO is not valid,
     * or with status 500 (Internal Server Error) if the userExtraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-extras")
    @Timed
    public ResponseEntity<UserExtraDTO> updateUserExtra(@Valid @RequestBody UserExtraDTO userExtraDTO) throws URISyntaxException {
        log.debug("REST request to update UserExtra : {}", userExtraDTO);
        if (userExtraDTO.getId() == null) {
            return createUserExtra(userExtraDTO);
        }
        UserExtraDTO result = userExtraService.save(userExtraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userExtraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-extras : get all the userExtras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userExtras in body
     */
    @GetMapping("/user-extras")
    @Timed
    public ResponseEntity<List<UserExtraDTO>> getAllUserExtras(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserExtras");
        Page<UserExtraDTO> page = userExtraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-extras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-extras/:id : get the "id" userExtra.
     *
     * @param id the id of the userExtraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExtraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-extras/{id}")
    @Timed
    public ResponseEntity<UserExtraDTO> getUserExtra(@PathVariable Long id) {
        log.debug("REST request to get UserExtra : {}", id);
        UserExtraDTO userExtraDTO = userExtraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userExtraDTO));
    }

    /**
     * DELETE  /user-extras/:id : delete the "id" userExtra.
     *
     * @param id the id of the userExtraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-extras/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserExtra(@PathVariable Long id) {
        log.debug("REST request to delete UserExtra : {}", id);
        userExtraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-extras?query=:query : search for the userExtra corresponding
     * to the query.
     *
     * @param query the query of the userExtra search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-extras")
    @Timed
    public ResponseEntity<List<UserExtraDTO>> searchUserExtras(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of UserExtras for query {}", query);
        Page<UserExtraDTO> page = userExtraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-extras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
