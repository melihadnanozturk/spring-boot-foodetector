package com.mao.foodetector.repository;

import com.mao.foodetector.entity.SoupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoupRepository extends JpaRepository<SoupEntity,Integer> {
    SoupEntity findBySoupName(String soupName);
    //Verilen malzemelere göre yemek dönen method :)
}
