package com.example.demo.repository;

import com.example.demo.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, String> {
}
