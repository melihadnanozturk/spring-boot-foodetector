package com.mao.foodetector.service;

import com.mao.foodetector.request.FoodRequest;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.FoodResponse;

//sadece ana desert classını etkiler
//malzeme için material service' ye git
public interface FoodService {
    Iterable<FoodResponse> getAll();
    FoodResponse getOne(String foodName);
    FoodResponse updateName(String variable, String foodName);
    DoneResponse delete(String foodName);
    DoneResponse newFood(FoodRequest request);
}
