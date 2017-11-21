package com.csse.ticketsystem.repository.search;

import com.csse.ticketsystem.domain.Seat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Seat entity.
 */
public interface SeatSearchRepository extends ElasticsearchRepository<Seat, Long> {
}
