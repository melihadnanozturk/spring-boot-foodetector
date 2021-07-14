package com.mao.foodetector.repository;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.FoodEntity;
import com.mao.foodetector.response.BaseResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodEntity,Integer> {
    FoodEntity findByFoodName(String foodname);
    //Verilen malzemelere göre yemek dönen method :)
}
