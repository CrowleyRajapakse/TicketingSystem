package com.csse.ticketsystem.repository.search;

import com.csse.ticketsystem.domain.Route;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Route entity.
 */
public interface RouteSearchRepository extends ElasticsearchRepository<Route, Long> {
}
