package com.mao.foodetector.service;

import com.mao.foodetector.request.DesertRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DesertResponse;

//sadece ana desert classını etkiler
//malzeme için material service' ye git
public interface DesertService {

    Iterable<DesertResponse> getAll();
    DesertResponse getOne(String desertName);
    BaseResponse updateName(String desertName, String newName);
    BaseResponse delete(String desertName);
    BaseResponse newDesert(DesertRequest request);
}
