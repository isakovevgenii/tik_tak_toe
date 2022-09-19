package com.example.ttt.entity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GameUpdating {
    private Integer gameId;
    private String playerName;
    private Integer vertical;
    private Integer horizontal;
}
