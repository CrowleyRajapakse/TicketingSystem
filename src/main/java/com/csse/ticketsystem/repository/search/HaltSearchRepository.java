package com.csse.ticketsystem.repository.search;

import com.csse.ticketsystem.domain.Halt;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Halt entity.
 */
public interface HaltSearchRepository extends ElasticsearchRepository<Halt, Long> {
}
