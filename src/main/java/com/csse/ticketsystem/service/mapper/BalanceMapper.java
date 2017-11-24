package com.csse.ticketsystem.service.mapper;

import com.csse.ticketsystem.domain.*;
import com.csse.ticketsystem.service.dto.BalanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Balance and its DTO BalanceDTO.
 */
@Mapper(componentModel = "spring", uses = {SmartCardMapper.class})
public interface BalanceMapper extends EntityMapper<BalanceDTO, Balance> {

    @Mapping(source = "smartCardID.id", target = "smartCardIDId")
    BalanceDTO toDto(Balance balance); 

    @Mapping(source = "smartCardIDId", target = "smartCardID")
    Balance toEntity(BalanceDTO balanceDTO);

    default Balance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Balance balance = new Balance();
        balance.setId(id);
        return balance;
    }
}
