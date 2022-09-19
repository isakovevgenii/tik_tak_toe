package com.example.ttt.entity.enums;

import lombok.Getter;

/**
 * List of marks used in the game
 */
@Getter
public enum TikTakToeMark {

    E(0),
    X(1),
    O(2);

    private final int code;

    TikTakToeMark(int code) {
        this.code = code;
    }
}