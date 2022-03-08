package com.cycleon.game.mancala.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "GAME")
public class Game extends EntityBase {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column
    private GameStatus gameStatus;

    @Column
    private Player playerOne;

    @Column
    private Player playerTwo;

    private Player playerTurn;

    @JsonIgnore
    private int currentPocketIndex;

}
