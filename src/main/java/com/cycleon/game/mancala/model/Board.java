package com.cycleon.game.mancala.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BOARD")
public class Board  extends  EntityBase{

    @OneToOne(mappedBy="board")
    private Game game;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "board", cascade= CascadeType.ALL)
    @JsonBackReference
    private List<Pocket> pockets;
}
