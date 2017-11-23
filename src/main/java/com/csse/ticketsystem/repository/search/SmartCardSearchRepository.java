package com.csse.ticketsystem.repository.search;

import com.csse.ticketsystem.domain.SmartCard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SmartCard entity.
 */
public interface SmartCardSearchRepository extends ElasticsearchRepository<SmartCard, Long> {
}
