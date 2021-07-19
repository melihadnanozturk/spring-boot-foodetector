package com.mao.foodetector.repository;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.FoodEntity;
import com.mao.foodetector.response.BaseResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<FoodEntity,Integer> {
    Optional<FoodEntity> findByFoodName(String foodname);
    //Verilen malzemelere göre yemek dönen method :)
}
