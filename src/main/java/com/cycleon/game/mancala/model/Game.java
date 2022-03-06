package com.cycleon.game.mancala.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "GAME")
public class Game  extends EntityBase {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="board_id")
    private Board board;

    @Column (columnDefinition = "boolean default false")
    private Boolean isOver;

    private PlayerTurn playerTurn;

    @JsonIgnore
    private int currentPocketIndex;

}
