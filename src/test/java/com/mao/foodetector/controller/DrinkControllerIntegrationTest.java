package com.mao.foodetector.controller;

import com.mao.foodetector.repository.DesertRepository;
import com.mao.foodetector.response.DrinkResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

//buradaki testler eğitim için hazılanmıştır,hiçbir geçerliliği yoktur.
@SpringBootTest
public class DrinkControllerIntegrationTest {

    @Autowired
    private DrinkController drinkController;

    @Autowired
    private DesertRepository desertRepository;

    @Test
    void testGetAll() {
        Integer size = desertRepository.findAll().stream().filter(entity -> entity.getId()%2 == 0).collect(Collectors.toList()).size();
        List<DrinkResponse> response =  drinkController.getAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(size, response.size());
    }
}