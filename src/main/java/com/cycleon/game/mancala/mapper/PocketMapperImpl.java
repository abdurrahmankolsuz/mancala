package com.cycleon.game.mancala.mapper;

import com.cycleon.game.mancala.dto.PocketDto;
import com.cycleon.game.mancala.model.Pocket;
import org.springframework.stereotype.Component;

@Component
public class PocketMapperImpl implements PocketMapper {

    @Override
    public PocketDto toDTO(Pocket entity) {
        PocketDto pocketDto = new PocketDto();
        pocketDto.setId(entity.getId());
        pocketDto.setPocketIdentifier(entity.getPocketIdentifier());
        pocketDto.setQuantityOfStones(entity.getQuantityOfStones());
        return pocketDto;
    }

    @Override
    public Pocket toEntity(PocketDto dto) {
        Pocket pocket = new Pocket();
        pocket.setId(dto.getId());
        pocket.setPocketIdentifier(dto.getPocketIdentifier());
        pocket.setQuantityOfStones(dto.getQuantityOfStones());
        return pocket;

    }
}
