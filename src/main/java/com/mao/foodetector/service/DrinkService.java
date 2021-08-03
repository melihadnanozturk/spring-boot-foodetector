package com.mao.foodetector.service;

import com.mao.foodetector.response.DesertResponse;
import com.mao.foodetector.response.DrinkResponse;

import java.util.List;

public interface DrinkService {
    List<DrinkResponse> getAll();

    void doNothing();

    DrinkResponse getOneDrink();

    void gereksizyazı();

    List<String> mylist();
}
