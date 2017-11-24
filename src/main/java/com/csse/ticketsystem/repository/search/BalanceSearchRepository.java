package com.csse.ticketsystem.repository.search;

import com.csse.ticketsystem.domain.Balance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Balance entity.
 */
public interface BalanceSearchRepository extends ElasticsearchRepository<Balance, Long> {
}
