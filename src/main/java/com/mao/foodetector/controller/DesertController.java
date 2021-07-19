package com.mao.foodetector.controller;

import com.mao.foodetector.request.DesertRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DesertResponse;
import com.mao.foodetector.service.DesertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deserts")
public class DesertController {

    private DesertService desertService;

    @Autowired
    public DesertController(DesertService desertService){
        this.desertService=desertService;
    }

    @GetMapping
    public Iterable<DesertResponse> getAll(){
        return desertService.getAll();
    }

    @GetMapping("/{desertname}")
    public BaseResponse getOne(@PathVariable String desertname){
        return desertService.getOne(desertname);
    }


    @PutMapping("/{desertname}/{newname}")
    public BaseResponse updateName(@PathVariable String desertname, @PathVariable String newname){
        return  desertService.updateName(desertname,newname);
    }

    @DeleteMapping("/{desertname}")
    public BaseResponse delete(@PathVariable String desertname){
        return desertService.delete(desertname);
    }

    @PostMapping
    public BaseResponse newDesert(@RequestBody DesertRequest request){
        return desertService.newDesert(request);
    }

}
