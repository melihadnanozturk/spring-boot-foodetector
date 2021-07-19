package com.mao.foodetector.repository;

import com.mao.foodetector.entity.SoupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SoupRepository extends JpaRepository<SoupEntity,Integer> {
    Optional<SoupEntity> findBySoupName(String soupName);
    //Verilen malzemelere göre yemek dönen method yazılıcak ileride :)
}
