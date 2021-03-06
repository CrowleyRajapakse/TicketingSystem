package com.csse.ticketsystem.repository;

import com.csse.ticketsystem.domain.SmartCard;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SmartCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmartCardRepository extends JpaRepository<SmartCard, Long> {

}
