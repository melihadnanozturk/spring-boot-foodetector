package com.mao.foodetector.controller;

import com.mao.foodetector.response.DrinkResponse;
import com.mao.foodetector.service.DrinkService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/drinks")
public class DrinkController {

    private final DrinkService drinkService;

    @Autowired
    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @SneakyThrows
    @GetMapping
    public List<DrinkResponse> getAll() {
        List<DrinkResponse> all = drinkService.getAll();

        drinkService.doNothing();

        if (all.isEmpty()) throw new Exception("hoppp");
        else drinkService.doNothing();

        return all;
    }

    @GetMapping("/forTest")
    public DrinkResponse getOneDrink() {
        for (int i=0;i<2;i++){
            drinkService.gereksizyazı();
        }
        return drinkService.getOneDrink();
    }

    @SneakyThrows
    @GetMapping("/test-list")
    public List<String> myList()  {
        if (drinkService.mylist().isEmpty()){
            throw new Exception();
        }
        return drinkService.mylist();
    }
}
