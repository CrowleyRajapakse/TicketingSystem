package com.csse.ticketsystem.repository.search;

import com.csse.ticketsystem.domain.Journey;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Journey entity.
 */
public interface JourneySearchRepository extends ElasticsearchRepository<Journey, Long> {
}
