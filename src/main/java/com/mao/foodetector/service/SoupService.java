package com.mao.foodetector.service;

import com.mao.foodetector.request.SoupRequest;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.SoupResponse;

//sadece ana desert classını etkiler
//malzeme için material service' ye git
public interface SoupService {
    Iterable<SoupResponse> getAll();
    SoupResponse getOne(String soupName);
    SoupResponse updateName(String newSoupName,String soupName);
    DoneResponse delete(String soupName);
    DoneResponse newSoup(SoupRequest request);
}
