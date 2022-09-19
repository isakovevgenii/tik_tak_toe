package com.example.ttt.repository;

import com.example.ttt.entity.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

}