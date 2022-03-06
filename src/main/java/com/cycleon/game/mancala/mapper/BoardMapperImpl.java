package com.cycleon.game.mancala.mapper;

import com.cycleon.game.mancala.dto.BoardDto;
import com.cycleon.game.mancala.dto.PocketDto;
import com.cycleon.game.mancala.model.Board;
import com.cycleon.game.mancala.model.Pocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardMapperImpl implements BoardMapper {

    private final PocketMapper pocketMapper;

    @Autowired
    public BoardMapperImpl(PocketMapper pocketMapper) {
        this.pocketMapper = pocketMapper;
    }

    @Override
    public BoardDto toDTO(Board entity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(entity.getId());
        List<PocketDto> pocketDtoList = new ArrayList<>();
        for (Pocket pocket :
                entity.getPockets()) {
            pocketDtoList.add(pocketMapper.toDTO(pocket));
        }
        boardDto.setPockets(pocketDtoList);
        return boardDto;
    }

    @Override
    public Board toEntity(BoardDto dto) {
        Board board = new Board();
        board.setId(dto.getId());
        List<Pocket> pocketList = new ArrayList<>();
        for (PocketDto pocketDto :
                dto.getPockets()) {
            pocketList.add(pocketMapper.toEntity(pocketDto));
        }
        board.setPockets(pocketList);
        return board;
    }
}
