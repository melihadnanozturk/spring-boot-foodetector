package com.mao.foodetector.controller;

import com.mao.foodetector.request.SoupRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.SoupResponse;
import com.mao.foodetector.service.SoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/soups")
public class SoupController {

    private SoupService soupService;

    @Autowired
    public SoupController(SoupService soupService){
        this.soupService=soupService;
    }

    @GetMapping("/getall")
    public Iterable<SoupResponse> getAll(){
        return soupService.getAll();
    }

    @GetMapping("/getone/{soupname}")
    public BaseResponse getOne(@PathVariable String soupname){
        return soupService.getOne(soupname);
    }

    @PutMapping("/updatename/{soupname}/{newsoupname}")
    public BaseResponse updateName(@PathVariable String newsoupname,@PathVariable String soupname){
        return soupService.updateName(newsoupname,soupname);
    }

    @DeleteMapping("/delete/{soupname}")
    public BaseResponse delete(@PathVariable String soupname){
        return soupService.delete(soupname);
    }

    @PostMapping("/newsoup")
    public BaseResponse newSoup(@RequestBody SoupRequest request){
        return soupService.newSoup(request);
    }
}
