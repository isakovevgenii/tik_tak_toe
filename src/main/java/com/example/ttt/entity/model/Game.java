package com.example.ttt.entity.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    private Integer id;
    private Integer scale;
    private String arrangement;
    private String playerName1;
    private String playerName2;
    private boolean isFinished;
}