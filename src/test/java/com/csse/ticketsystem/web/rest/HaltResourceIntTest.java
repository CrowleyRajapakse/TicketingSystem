package com.csse.ticketsystem.web.rest;

import com.csse.ticketsystem.TicketingSystemApp;

import com.csse.ticketsystem.domain.Halt;
import com.csse.ticketsystem.domain.Route;
import com.csse.ticketsystem.repository.HaltRepository;
import com.csse.ticketsystem.service.HaltService;
import com.csse.ticketsystem.repository.search.HaltSearchRepository;
import com.csse.ticketsystem.service.dto.HaltDTO;
import com.csse.ticketsystem.service.mapper.HaltMapper;
import com.csse.ticketsystem.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.csse.ticketsystem.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HaltResource REST controller.
 *
 * @see HaltResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketingSystemApp.class)
public class HaltResourceIntTest {

    private static final String DEFAULT_START_HALT = "AAAAAAAAAA";
    private static final String UPDATED_START_HALT = "BBBBBBBBBB";

    private static final String DEFAULT_END_HALT = "AAAAAAAAAA";
    private static final String UPDATED_END_HALT = "BBBBBBBBBB";

    private static final Double DEFAULT_HALT_DISTANCE = 1D;
    private static final Double UPDATED_HALT_DISTANCE = 2D;

    private static final Double DEFAULT_PRICE = 0D;
    private static final Double UPDATED_PRICE = 1D;

    @Autowired
    private HaltRepository haltRepository;

    @Autowired
    private HaltMapper haltMapper;

    @Autowired
    private HaltService haltService;

    @Autowired
    private HaltSearchRepository haltSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHaltMockMvc;

    private Halt halt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HaltResource haltResource = new HaltResource(haltService);
        this.restHaltMockMvc = MockMvcBuilders.standaloneSetup(haltResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Halt createEntity(EntityManager em) {
        Halt halt = new Halt()
            .start_halt(DEFAULT_START_HALT)
            .end_halt(DEFAULT_END_HALT)
            .halt_distance(DEFAULT_HALT_DISTANCE)
            .price(DEFAULT_PRICE);
        // Add required entity
        Route route = RouteResourceIntTest.createEntity(em);
        em.persist(route);
        em.flush();
        halt.setRoute(route);
        return halt;
    }

    @Before
    public void initTest() {
        haltSearchRepository.deleteAll();
        halt = createEntity(em);
    }

    @Test
    @Transactional
    public void createHalt() throws Exception {
        int databaseSizeBeforeCreate = haltRepository.findAll().size();

        // Create the Halt
        HaltDTO haltDTO = haltMapper.toDto(halt);
        restHaltMockMvc.perform(post("/api/halts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(haltDTO)))
            .andExpect(status().isCreated());

        // Validate the Halt in the database
        List<Halt> haltList = haltRepository.findAll();
        assertThat(haltList).hasSize(databaseSizeBeforeCreate + 1);
        Halt testHalt = haltList.get(haltList.size() - 1);
        assertThat(testHalt.getStart_halt()).isEqualTo(DEFAULT_START_HALT);
        assertThat(testHalt.getEnd_halt()).isEqualTo(DEFAULT_END_HALT);
        assertThat(testHalt.getHalt_distance()).isEqualTo(DEFAULT_HALT_DISTANCE);
        assertThat(testHalt.getPrice()).isEqualTo(DEFAULT_PRICE);

        // Validate the Halt in Elasticsearch
        Halt haltEs = haltSearchRepository.findOne(testHalt.getId());
        assertThat(haltEs).isEqualToComparingFieldByField(testHalt);
    }

    @Test
    @Transactional
    public void createHaltWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = haltRepository.findAll().size();

        // Create the Halt with an existing ID
        halt.setId(1L);
        HaltDTO haltDTO = haltMapper.toDto(halt);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHaltMockMvc.perform(post("/api/halts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(haltDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Halt in the database
        List<Halt> haltList = haltRepository.findAll();
        assertThat(haltList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStart_haltIsRequired() throws Exception {
        int databaseSizeBeforeTest = haltRepository.findAll().size();
        // set the field null
        halt.setStart_halt(null);

        // Create the Halt, which fails.
        HaltDTO haltDTO = haltMapper.toDto(halt);

        restHaltMockMvc.perform(post("/api/halts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(haltDTO)))
            .andExpect(status().isBadRequest());

        List<Halt> haltList = haltRepository.findAll();
        assertThat(haltList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnd_haltIsRequired() throws Exception {
        int databaseSizeBeforeTest = haltRepository.findAll().size();
        // set the field null
        halt.setEnd_halt(null);

        // Create the Halt, which fails.
        HaltDTO haltDTO = haltMapper.toDto(halt);

        restHaltMockMvc.perform(post("/api/halts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(haltDTO)))
            .andExpect(status().isBadRequest());

        List<Halt> haltList = haltRepository.findAll();
        assertThat(haltList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHalt_distanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = haltRepository.findAll().size();
        // set the field null
        halt.setHalt_distance(null);

        // Create the Halt, which fails.
        HaltDTO haltDTO = haltMapper.toDto(halt);

        restHaltMockMvc.perform(post("/api/halts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(haltDTO)))
            .andExpect(status().isBadRequest());

        List<Halt> haltList = haltRepository.findAll();
        assertThat(haltList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = haltRepository.findAll().size();
        // set the field null
        halt.setPrice(null);

        // Create the Halt, which fails.
        HaltDTO haltDTO = haltMapper.toDto(halt);

        restHaltMockMvc.perform(post("/api/halts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(haltDTO)))
            .andExpect(status().isBadRequest());

        List<Halt> haltList = haltRepository.findAll();
        assertThat(haltList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHalts() throws Exception {
        // Initialize the database
        haltRepository.saveAndFlush(halt);

        // Get all the haltList
        restHaltMockMvc.perform(get("/api/halts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(halt.getId().intValue())))
            .andExpect(jsonPath("$.[*].start_halt").value(hasItem(DEFAULT_START_HALT.toString())))
            .andExpect(jsonPath("$.[*].end_halt").value(hasItem(DEFAULT_END_HALT.toString())))
            .andExpect(jsonPath("$.[*].halt_distance").value(hasItem(DEFAULT_HALT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getHalt() throws Exception {
        // Initialize the database
        haltRepository.saveAndFlush(halt);

        // Get the halt
        restHaltMockMvc.perform(get("/api/halts/{id}", halt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(halt.getId().intValue()))
            .andExpect(jsonPath("$.start_halt").value(DEFAULT_START_HALT.toString()))
            .andExpect(jsonPath("$.end_halt").value(DEFAULT_END_HALT.toString()))
            .andExpect(jsonPath("$.halt_distance").value(DEFAULT_HALT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHalt() throws Exception {
        // Get the halt
        restHaltMockMvc.perform(get("/api/halts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHalt() throws Exception {
        // Initialize the database
        haltRepository.saveAndFlush(halt);
        haltSearchRepository.save(halt);
        int databaseSizeBeforeUpdate = haltRepository.findAll().size();

        // Update the halt
        Halt updatedHalt = haltRepository.findOne(halt.getId());
        updatedHalt
            .start_halt(UPDATED_START_HALT)
            .end_halt(UPDATED_END_HALT)
            .halt_distance(UPDATED_HALT_DISTANCE)
            .price(UPDATED_PRICE);
        HaltDTO haltDTO = haltMapper.toDto(updatedHalt);

        restHaltMockMvc.perform(put("/api/halts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(haltDTO)))
            .andExpect(status().isOk());

        // Validate the Halt in the database
        List<Halt> haltList = haltRepository.findAll();
        assertThat(haltList).hasSize(databaseSizeBeforeUpdate);
        Halt testHalt = haltList.get(haltList.size() - 1);
        assertThat(testHalt.getStart_halt()).isEqualTo(UPDATED_START_HALT);
        assertThat(testHalt.getEnd_halt()).isEqualTo(UPDATED_END_HALT);
        assertThat(testHalt.getHalt_distance()).isEqualTo(UPDATED_HALT_DISTANCE);
        assertThat(testHalt.getPrice()).isEqualTo(UPDATED_PRICE);

        // Validate the Halt in Elasticsearch
        Halt haltEs = haltSearchRepository.findOne(testHalt.getId());
        assertThat(haltEs).isEqualToComparingFieldByField(testHalt);
    }

    @Test
    @Transactional
    public void updateNonExistingHalt() throws Exception {
        int databaseSizeBeforeUpdate = haltRepository.findAll().size();

        // Create the Halt
        HaltDTO haltDTO = haltMapper.toDto(halt);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHaltMockMvc.perform(put("/api/halts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(haltDTO)))
            .andExpect(status().isCreated());

        // Validate the Halt in the database
        List<Halt> haltList = haltRepository.findAll();
        assertThat(haltList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHalt() throws Exception {
        // Initialize the database
        haltRepository.saveAndFlush(halt);
        haltSearchRepository.save(halt);
        int databaseSizeBeforeDelete = haltRepository.findAll().size();

        // Get the halt
        restHaltMockMvc.perform(delete("/api/halts/{id}", halt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean haltExistsInEs = haltSearchRepository.exists(halt.getId());
        assertThat(haltExistsInEs).isFalse();

        // Validate the database is empty
        List<Halt> haltList = haltRepository.findAll();
        assertThat(haltList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHalt() throws Exception {
        // Initialize the database
        haltRepository.saveAndFlush(halt);
        haltSearchRepository.save(halt);

        // Search the halt
        restHaltMockMvc.perform(get("/api/_search/halts?query=id:" + halt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(halt.getId().intValue())))
            .andExpect(jsonPath("$.[*].start_halt").value(hasItem(DEFAULT_START_HALT.toString())))
            .andExpect(jsonPath("$.[*].end_halt").value(hasItem(DEFAULT_END_HALT.toString())))
            .andExpect(jsonPath("$.[*].halt_distance").value(hasItem(DEFAULT_HALT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Halt.class);
        Halt halt1 = new Halt();
        halt1.setId(1L);
        Halt halt2 = new Halt();
        halt2.setId(halt1.getId());
        assertThat(halt1).isEqualTo(halt2);
        halt2.setId(2L);
        assertThat(halt1).isNotEqualTo(halt2);
        halt1.setId(null);
        assertThat(halt1).isNotEqualTo(halt2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HaltDTO.class);
        HaltDTO haltDTO1 = new HaltDTO();
        haltDTO1.setId(1L);
        HaltDTO haltDTO2 = new HaltDTO();
        assertThat(haltDTO1).isNotEqualTo(haltDTO2);
        haltDTO2.setId(haltDTO1.getId());
        assertThat(haltDTO1).isEqualTo(haltDTO2);
        haltDTO2.setId(2L);
        assertThat(haltDTO1).isNotEqualTo(haltDTO2);
        haltDTO1.setId(null);
        assertThat(haltDTO1).isNotEqualTo(haltDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(haltMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(haltMapper.fromId(null)).isNull();
    }
}
