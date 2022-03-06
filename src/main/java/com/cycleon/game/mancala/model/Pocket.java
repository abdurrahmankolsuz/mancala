package com.cycleon.game.mancala.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "POCKET")
public class Pocket extends EntityBase {

    private Integer pocketIdentifier;

    private Integer quantityOfStones;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id", insertable = true, updatable = false)
    @JsonManagedReference
    private Board board;

}
