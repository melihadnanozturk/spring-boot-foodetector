package com.mao.foodetector.service;

import com.mao.foodetector.request.DesertRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DesertResponse;
import com.mao.foodetector.response.DoneResponse;

import java.util.List;

//sadece ana desert classını etkiler
//malzeme için material service' ye git
public interface DesertService {

    Iterable<DesertResponse> getAll(List<DesertResponse> liste, DesertResponse response);
    DesertResponse getOne(String desertName);
    DesertResponse updateName(String desertName, String newName);
    DoneResponse delete(String desertName);
    DoneResponse newDesert(DesertRequest request);
}
