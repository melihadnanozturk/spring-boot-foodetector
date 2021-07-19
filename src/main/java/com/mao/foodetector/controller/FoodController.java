package com.mao.foodetector.controller;

import com.mao.foodetector.request.FoodRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.FoodResponse;
import com.mao.foodetector.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foods")
public class FoodController {

    private FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService){
        this.foodService=foodService;
    }

    @GetMapping
    public Iterable<FoodResponse> getAll(){
        return foodService.getAll();
    }

    @GetMapping("/{foodname}")
    public BaseResponse getOne(@PathVariable  String foodname){
        return foodService.getOne(foodname);
    }

    @PutMapping("/{foodname}/{newname}")
    public BaseResponse updateName(@PathVariable String foodname,@PathVariable String newname){
        return foodService.updateName(foodname,newname);
    }

    @DeleteMapping("/{foodname}")
    public BaseResponse delete(@PathVariable String foodname){
        return foodService.delete(foodname);
    }

    @PostMapping
    public BaseResponse newDesert(@RequestBody FoodRequest request){
        return foodService.newFood(request);
    }
}
