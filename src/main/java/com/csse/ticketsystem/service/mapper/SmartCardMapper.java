package com.csse.ticketsystem.service.mapper;

import com.csse.ticketsystem.domain.*;
import com.csse.ticketsystem.service.dto.SmartCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SmartCard and its DTO SmartCardDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SmartCardMapper extends EntityMapper<SmartCardDTO, SmartCard> {

    

    

    default SmartCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        SmartCard smartCard = new SmartCard();
        smartCard.setId(id);
        return smartCard;
    }
}
