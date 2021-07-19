package com.mao.foodetector.repository;

import com.mao.foodetector.entity.DesertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesertRepository extends JpaRepository<DesertEntity,Integer> {
    Optional<DesertEntity> findByDesertName(String desertName);
    //Verilen malzemelere göre yemek dönen method :)
}
