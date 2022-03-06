package com.cycleon.game.mancala.mapper;

public interface BaseMapper<E,D> {

    D toDTO(E entity);

    E toEntity(D dto);
}
