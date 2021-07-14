package com.mao.foodetector.repository;

import com.mao.foodetector.entity.DesertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesertRepository extends JpaRepository<DesertEntity,Integer> {
    DesertEntity findByDesertName(String desertName);
    //Verilen malzemelere göre yemek dönen method :)
}
