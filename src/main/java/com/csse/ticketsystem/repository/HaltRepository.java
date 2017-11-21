package com.csse.ticketsystem.repository;

import com.csse.ticketsystem.domain.Halt;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Halt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HaltRepository extends JpaRepository<Halt, Long> {

}
