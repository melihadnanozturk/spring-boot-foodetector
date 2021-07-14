package com.mao.foodetector.service;

import com.mao.foodetector.request.FoodRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.FoodResponse;

//sadece ana desert classını etkiler
//malzeme için material service' ye git
public interface FoodService {
    Iterable<FoodResponse> getAll();
    BaseResponse getOne(String foodName);
    BaseResponse updateName(String variable, String foodName);
    BaseResponse delete(String foodName);
    BaseResponse newFood(FoodRequest request);
}
