package com.mao.foodetector.service;

import com.mao.foodetector.request.SoupRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.SoupResponse;

//sadece ana desert classını etkiler
//malzeme için material service' ye git
public interface SoupService {
    Iterable<SoupResponse> getAll();
    BaseResponse getOne(String soupName);
    BaseResponse updateName(String newSoupName,String soupName);
    BaseResponse delete(String soupName);
    BaseResponse newSoup(SoupRequest request);
}
