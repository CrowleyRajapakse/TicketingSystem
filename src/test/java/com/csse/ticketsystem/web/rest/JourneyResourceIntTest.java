package com.csse.ticketsystem.web.rest;

import com.csse.ticketsystem.TicketingSystemApp;

import com.csse.ticketsystem.domain.Journey;
import com.csse.ticketsystem.domain.Vehicle;
import com.csse.ticketsystem.repository.JourneyRepository;
import com.csse.ticketsystem.service.JourneyService;
import com.csse.ticketsystem.repository.search.JourneySearchRepository;
import com.csse.ticketsystem.service.dto.JourneyDTO;
import com.csse.ticketsystem.service.mapper.JourneyMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.csse.ticketsystem.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JourneyResource REST controller.
 *
 * @see JourneyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketingSystemApp.class)
public class JourneyResourceIntTest {

    private static final String DEFAULT_JOURNEY_ID = "AAAAAAAAAA";
    private static final String UPDATED_JOURNEY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTURE = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTURE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DEPARTURE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEPARTURE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ARRIVAL = "AAAAAAAAAA";
    private static final String UPDATED_ARRIVAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ARRIVAL_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ARRIVAL_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 0D;
    private static final Double UPDATED_AMOUNT = 1D;

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private JourneyMapper journeyMapper;

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private JourneySearchRepository journeySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJourneyMockMvc;

    private Journey journey;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JourneyResource journeyResource = new JourneyResource(journeyService);
        this.restJourneyMockMvc = MockMvcBuilders.standaloneSetup(journeyResource)
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
    public static Journey createEntity(EntityManager em) {
        Journey journey = new Journey()
            .journey_id(DEFAULT_JOURNEY_ID)
            .departure(DEFAULT_DEPARTURE)
            .departure_time(DEFAULT_DEPARTURE_TIME)
            .arrival(DEFAULT_ARRIVAL)
            .arrival_time(DEFAULT_ARRIVAL_TIME)
            .amount(DEFAULT_AMOUNT);
        // Add required entity
        Vehicle vehicle = VehicleResourceIntTest.createEntity(em);
        em.persist(vehicle);
        em.flush();
        journey.setVehicle(vehicle);
        return journey;
    }

    @Before
    public void initTest() {
        journeySearchRepository.deleteAll();
        journey = createEntity(em);
    }

    @Test
    @Transactional
    public void createJourney() throws Exception {
        int databaseSizeBeforeCreate = journeyRepository.findAll().size();

        // Create the Journey
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);
        restJourneyMockMvc.perform(post("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isCreated());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeCreate + 1);
        Journey testJourney = journeyList.get(journeyList.size() - 1);
        assertThat(testJourney.getJourney_id()).isEqualTo(DEFAULT_JOURNEY_ID);
        assertThat(testJourney.getDeparture()).isEqualTo(DEFAULT_DEPARTURE);
        assertThat(testJourney.getDeparture_time()).isEqualTo(DEFAULT_DEPARTURE_TIME);
        assertThat(testJourney.getArrival()).isEqualTo(DEFAULT_ARRIVAL);
        assertThat(testJourney.getArrival_time()).isEqualTo(DEFAULT_ARRIVAL_TIME);
        assertThat(testJourney.getAmount()).isEqualTo(DEFAULT_AMOUNT);

        // Validate the Journey in Elasticsearch
        Journey journeyEs = journeySearchRepository.findOne(testJourney.getId());
        assertThat(journeyEs).isEqualToComparingFieldByField(testJourney);
    }

    @Test
    @Transactional
    public void createJourneyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journeyRepository.findAll().size();

        // Create the Journey with an existing ID
        journey.setId(1L);
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneyMockMvc.perform(post("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkJourney_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setJourney_id(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc.perform(post("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepartureIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setDeparture(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc.perform(post("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeparture_timeIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setDeparture_time(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc.perform(post("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJourneys() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        // Get all the journeyList
        restJourneyMockMvc.perform(get("/api/journeys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journey.getId().intValue())))
            .andExpect(jsonPath("$.[*].journey_id").value(hasItem(DEFAULT_JOURNEY_ID.toString())))
            .andExpect(jsonPath("$.[*].departure").value(hasItem(DEFAULT_DEPARTURE.toString())))
            .andExpect(jsonPath("$.[*].departure_time").value(hasItem(DEFAULT_DEPARTURE_TIME.toString())))
            .andExpect(jsonPath("$.[*].arrival").value(hasItem(DEFAULT_ARRIVAL.toString())))
            .andExpect(jsonPath("$.[*].arrival_time").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        // Get the journey
        restJourneyMockMvc.perform(get("/api/journeys/{id}", journey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journey.getId().intValue()))
            .andExpect(jsonPath("$.journey_id").value(DEFAULT_JOURNEY_ID.toString()))
            .andExpect(jsonPath("$.departure").value(DEFAULT_DEPARTURE.toString()))
            .andExpect(jsonPath("$.departure_time").value(DEFAULT_DEPARTURE_TIME.toString()))
            .andExpect(jsonPath("$.arrival").value(DEFAULT_ARRIVAL.toString()))
            .andExpect(jsonPath("$.arrival_time").value(DEFAULT_ARRIVAL_TIME.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJourney() throws Exception {
        // Get the journey
        restJourneyMockMvc.perform(get("/api/journeys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);
        journeySearchRepository.save(journey);
        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();

        // Update the journey
        Journey updatedJourney = journeyRepository.findOne(journey.getId());
        updatedJourney
            .journey_id(UPDATED_JOURNEY_ID)
            .departure(UPDATED_DEPARTURE)
            .departure_time(UPDATED_DEPARTURE_TIME)
            .arrival(UPDATED_ARRIVAL)
            .arrival_time(UPDATED_ARRIVAL_TIME)
            .amount(UPDATED_AMOUNT);
        JourneyDTO journeyDTO = journeyMapper.toDto(updatedJourney);

        restJourneyMockMvc.perform(put("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isOk());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
        Journey testJourney = journeyList.get(journeyList.size() - 1);
        assertThat(testJourney.getJourney_id()).isEqualTo(UPDATED_JOURNEY_ID);
        assertThat(testJourney.getDeparture()).isEqualTo(UPDATED_DEPARTURE);
        assertThat(testJourney.getDeparture_time()).isEqualTo(UPDATED_DEPARTURE_TIME);
        assertThat(testJourney.getArrival()).isEqualTo(UPDATED_ARRIVAL);
        assertThat(testJourney.getArrival_time()).isEqualTo(UPDATED_ARRIVAL_TIME);
        assertThat(testJourney.getAmount()).isEqualTo(UPDATED_AMOUNT);

        // Validate the Journey in Elasticsearch
        Journey journeyEs = journeySearchRepository.findOne(testJourney.getId());
        assertThat(journeyEs).isEqualToComparingFieldByField(testJourney);
    }

    @Test
    @Transactional
    public void updateNonExistingJourney() throws Exception {
        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();

        // Create the Journey
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJourneyMockMvc.perform(put("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isCreated());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);
        journeySearchRepository.save(journey);
        int databaseSizeBeforeDelete = journeyRepository.findAll().size();

        // Get the journey
        restJourneyMockMvc.perform(delete("/api/journeys/{id}", journey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean journeyExistsInEs = journeySearchRepository.exists(journey.getId());
        assertThat(journeyExistsInEs).isFalse();

        // Validate the database is empty
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);
        journeySearchRepository.save(journey);

        // Search the journey
        restJourneyMockMvc.perform(get("/api/_search/journeys?query=id:" + journey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journey.getId().intValue())))
            .andExpect(jsonPath("$.[*].journey_id").value(hasItem(DEFAULT_JOURNEY_ID.toString())))
            .andExpect(jsonPath("$.[*].departure").value(hasItem(DEFAULT_DEPARTURE.toString())))
            .andExpect(jsonPath("$.[*].departure_time").value(hasItem(DEFAULT_DEPARTURE_TIME.toString())))
            .andExpect(jsonPath("$.[*].arrival").value(hasItem(DEFAULT_ARRIVAL.toString())))
            .andExpect(jsonPath("$.[*].arrival_time").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Journey.class);
        Journey journey1 = new Journey();
        journey1.setId(1L);
        Journey journey2 = new Journey();
        journey2.setId(journey1.getId());
        assertThat(journey1).isEqualTo(journey2);
        journey2.setId(2L);
        assertThat(journey1).isNotEqualTo(journey2);
        journey1.setId(null);
        assertThat(journey1).isNotEqualTo(journey2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyDTO.class);
        JourneyDTO journeyDTO1 = new JourneyDTO();
        journeyDTO1.setId(1L);
        JourneyDTO journeyDTO2 = new JourneyDTO();
        assertThat(journeyDTO1).isNotEqualTo(journeyDTO2);
        journeyDTO2.setId(journeyDTO1.getId());
        assertThat(journeyDTO1).isEqualTo(journeyDTO2);
        journeyDTO2.setId(2L);
        assertThat(journeyDTO1).isNotEqualTo(journeyDTO2);
        journeyDTO1.setId(null);
        assertThat(journeyDTO1).isNotEqualTo(journeyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(journeyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(journeyMapper.fromId(null)).isNull();
    }
}
