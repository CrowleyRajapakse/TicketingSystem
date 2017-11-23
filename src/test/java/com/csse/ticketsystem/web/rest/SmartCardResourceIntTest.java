package com.csse.ticketsystem.web.rest;

import com.csse.ticketsystem.TicketingSystemApp;

import com.csse.ticketsystem.domain.SmartCard;
import com.csse.ticketsystem.repository.SmartCardRepository;
import com.csse.ticketsystem.service.SmartCardService;
import com.csse.ticketsystem.repository.search.SmartCardSearchRepository;
import com.csse.ticketsystem.service.dto.SmartCardDTO;
import com.csse.ticketsystem.service.mapper.SmartCardMapper;
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
 * Test class for the SmartCardResource REST controller.
 *
 * @see SmartCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketingSystemApp.class)
public class SmartCardResourceIntTest {

    private static final String DEFAULT_SMART_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_SMART_CARD_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private SmartCardRepository smartCardRepository;

    @Autowired
    private SmartCardMapper smartCardMapper;

    @Autowired
    private SmartCardService smartCardService;

    @Autowired
    private SmartCardSearchRepository smartCardSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSmartCardMockMvc;

    private SmartCard smartCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SmartCardResource smartCardResource = new SmartCardResource(smartCardService);
        this.restSmartCardMockMvc = MockMvcBuilders.standaloneSetup(smartCardResource)
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
    public static SmartCard createEntity(EntityManager em) {
        SmartCard smartCard = new SmartCard()
            .smartCardID(DEFAULT_SMART_CARD_ID)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .status(DEFAULT_STATUS);
        return smartCard;
    }

    @Before
    public void initTest() {
        smartCardSearchRepository.deleteAll();
        smartCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createSmartCard() throws Exception {
        int databaseSizeBeforeCreate = smartCardRepository.findAll().size();

        // Create the SmartCard
        SmartCardDTO smartCardDTO = smartCardMapper.toDto(smartCard);
        restSmartCardMockMvc.perform(post("/api/smart-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smartCardDTO)))
            .andExpect(status().isCreated());

        // Validate the SmartCard in the database
        List<SmartCard> smartCardList = smartCardRepository.findAll();
        assertThat(smartCardList).hasSize(databaseSizeBeforeCreate + 1);
        SmartCard testSmartCard = smartCardList.get(smartCardList.size() - 1);
        assertThat(testSmartCard.getSmartCardID()).isEqualTo(DEFAULT_SMART_CARD_ID);
        assertThat(testSmartCard.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testSmartCard.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the SmartCard in Elasticsearch
        SmartCard smartCardEs = smartCardSearchRepository.findOne(testSmartCard.getId());
        assertThat(smartCardEs).isEqualToComparingFieldByField(testSmartCard);
    }

    @Test
    @Transactional
    public void createSmartCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = smartCardRepository.findAll().size();

        // Create the SmartCard with an existing ID
        smartCard.setId(1L);
        SmartCardDTO smartCardDTO = smartCardMapper.toDto(smartCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmartCardMockMvc.perform(post("/api/smart-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smartCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SmartCard in the database
        List<SmartCard> smartCardList = smartCardRepository.findAll();
        assertThat(smartCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSmartCardIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = smartCardRepository.findAll().size();
        // set the field null
        smartCard.setSmartCardID(null);

        // Create the SmartCard, which fails.
        SmartCardDTO smartCardDTO = smartCardMapper.toDto(smartCard);

        restSmartCardMockMvc.perform(post("/api/smart-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smartCardDTO)))
            .andExpect(status().isBadRequest());

        List<SmartCard> smartCardList = smartCardRepository.findAll();
        assertThat(smartCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = smartCardRepository.findAll().size();
        // set the field null
        smartCard.setExpiryDate(null);

        // Create the SmartCard, which fails.
        SmartCardDTO smartCardDTO = smartCardMapper.toDto(smartCard);

        restSmartCardMockMvc.perform(post("/api/smart-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smartCardDTO)))
            .andExpect(status().isBadRequest());

        List<SmartCard> smartCardList = smartCardRepository.findAll();
        assertThat(smartCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = smartCardRepository.findAll().size();
        // set the field null
        smartCard.setStatus(null);

        // Create the SmartCard, which fails.
        SmartCardDTO smartCardDTO = smartCardMapper.toDto(smartCard);

        restSmartCardMockMvc.perform(post("/api/smart-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smartCardDTO)))
            .andExpect(status().isBadRequest());

        List<SmartCard> smartCardList = smartCardRepository.findAll();
        assertThat(smartCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmartCards() throws Exception {
        // Initialize the database
        smartCardRepository.saveAndFlush(smartCard);

        // Get all the smartCardList
        restSmartCardMockMvc.perform(get("/api/smart-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smartCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].smartCardID").value(hasItem(DEFAULT_SMART_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSmartCard() throws Exception {
        // Initialize the database
        smartCardRepository.saveAndFlush(smartCard);

        // Get the smartCard
        restSmartCardMockMvc.perform(get("/api/smart-cards/{id}", smartCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(smartCard.getId().intValue()))
            .andExpect(jsonPath("$.smartCardID").value(DEFAULT_SMART_CARD_ID.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSmartCard() throws Exception {
        // Get the smartCard
        restSmartCardMockMvc.perform(get("/api/smart-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmartCard() throws Exception {
        // Initialize the database
        smartCardRepository.saveAndFlush(smartCard);
        smartCardSearchRepository.save(smartCard);
        int databaseSizeBeforeUpdate = smartCardRepository.findAll().size();

        // Update the smartCard
        SmartCard updatedSmartCard = smartCardRepository.findOne(smartCard.getId());
        updatedSmartCard
            .smartCardID(UPDATED_SMART_CARD_ID)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .status(UPDATED_STATUS);
        SmartCardDTO smartCardDTO = smartCardMapper.toDto(updatedSmartCard);

        restSmartCardMockMvc.perform(put("/api/smart-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smartCardDTO)))
            .andExpect(status().isOk());

        // Validate the SmartCard in the database
        List<SmartCard> smartCardList = smartCardRepository.findAll();
        assertThat(smartCardList).hasSize(databaseSizeBeforeUpdate);
        SmartCard testSmartCard = smartCardList.get(smartCardList.size() - 1);
        assertThat(testSmartCard.getSmartCardID()).isEqualTo(UPDATED_SMART_CARD_ID);
        assertThat(testSmartCard.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testSmartCard.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the SmartCard in Elasticsearch
        SmartCard smartCardEs = smartCardSearchRepository.findOne(testSmartCard.getId());
        assertThat(smartCardEs).isEqualToComparingFieldByField(testSmartCard);
    }

    @Test
    @Transactional
    public void updateNonExistingSmartCard() throws Exception {
        int databaseSizeBeforeUpdate = smartCardRepository.findAll().size();

        // Create the SmartCard
        SmartCardDTO smartCardDTO = smartCardMapper.toDto(smartCard);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSmartCardMockMvc.perform(put("/api/smart-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smartCardDTO)))
            .andExpect(status().isCreated());

        // Validate the SmartCard in the database
        List<SmartCard> smartCardList = smartCardRepository.findAll();
        assertThat(smartCardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSmartCard() throws Exception {
        // Initialize the database
        smartCardRepository.saveAndFlush(smartCard);
        smartCardSearchRepository.save(smartCard);
        int databaseSizeBeforeDelete = smartCardRepository.findAll().size();

        // Get the smartCard
        restSmartCardMockMvc.perform(delete("/api/smart-cards/{id}", smartCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean smartCardExistsInEs = smartCardSearchRepository.exists(smartCard.getId());
        assertThat(smartCardExistsInEs).isFalse();

        // Validate the database is empty
        List<SmartCard> smartCardList = smartCardRepository.findAll();
        assertThat(smartCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSmartCard() throws Exception {
        // Initialize the database
        smartCardRepository.saveAndFlush(smartCard);
        smartCardSearchRepository.save(smartCard);

        // Search the smartCard
        restSmartCardMockMvc.perform(get("/api/_search/smart-cards?query=id:" + smartCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smartCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].smartCardID").value(hasItem(DEFAULT_SMART_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmartCard.class);
        SmartCard smartCard1 = new SmartCard();
        smartCard1.setId(1L);
        SmartCard smartCard2 = new SmartCard();
        smartCard2.setId(smartCard1.getId());
        assertThat(smartCard1).isEqualTo(smartCard2);
        smartCard2.setId(2L);
        assertThat(smartCard1).isNotEqualTo(smartCard2);
        smartCard1.setId(null);
        assertThat(smartCard1).isNotEqualTo(smartCard2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmartCardDTO.class);
        SmartCardDTO smartCardDTO1 = new SmartCardDTO();
        smartCardDTO1.setId(1L);
        SmartCardDTO smartCardDTO2 = new SmartCardDTO();
        assertThat(smartCardDTO1).isNotEqualTo(smartCardDTO2);
        smartCardDTO2.setId(smartCardDTO1.getId());
        assertThat(smartCardDTO1).isEqualTo(smartCardDTO2);
        smartCardDTO2.setId(2L);
        assertThat(smartCardDTO1).isNotEqualTo(smartCardDTO2);
        smartCardDTO1.setId(null);
        assertThat(smartCardDTO1).isNotEqualTo(smartCardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(smartCardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(smartCardMapper.fromId(null)).isNull();
    }
}
